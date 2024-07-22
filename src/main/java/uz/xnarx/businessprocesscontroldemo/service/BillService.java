package uz.xnarx.businessprocesscontroldemo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.xnarx.businessprocesscontroldemo.Entity.*;
import uz.xnarx.businessprocesscontroldemo.exception.NotFoundException;
import uz.xnarx.businessprocesscontroldemo.payload.BillDto;
import uz.xnarx.businessprocesscontroldemo.payload.OrderDto;
import uz.xnarx.businessprocesscontroldemo.repository.BillHistoryRepository;
import uz.xnarx.businessprocesscontroldemo.repository.BillRepository;
import uz.xnarx.businessprocesscontroldemo.repository.OrderRepository;
import uz.xnarx.businessprocesscontroldemo.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;
    private final BillHistoryRepository billHistoryRepository;

    @Transactional
    public BillDto saveOrModifyBill(BillDto billDto) {

        Bill bill;
        if (billDto.getId() != null) {
            bill = billRepository.findById(billDto.getId()).orElseThrow(() -> new NotFoundException("Bill not found"));
            bill.setModifiedDate(LocalDateTime.now());
            bill.setBorrowedAmount(bill.getBorrowedAmount() - billDto.getPaidAmount());
            bill.setPaidAmount(bill.getPaidAmount() + billDto.getPaidAmount());
            billRepository.save(bill);
            saveToHistory(bill, billDto.getPaidAmount());
            return mapBillToDto(bill);
        }
        bill = new Bill();
        bill.setClient(objectMapper.convertValue(billDto.getClientId(), Client.class));
        bill.setCreatedDate(LocalDateTime.now());
        bill.setModifiedDate(null);
        List<Order> orders = billDto.getOrderDtos().stream().map(this::mapToEntity).toList();
        for (Order order : orders) {
            order.setBill(bill);
        }
        bill.setOrders(orders);
        bill.setTotalPrice(calculateTotalAmount(billDto.getOrderDtos()));
        bill.setBorrowedAmount(calculateTotalAmount(billDto.getOrderDtos()));
        bill.setPaidAmount(0.0);

        billRepository.save(bill);
        saveToHistory(bill, billDto.getPaidAmount());
        return mapBillToDto(bill);
    }

    private void saveToHistory(Bill bill, Double payingAmount) {
        BillHistory billHistory = new BillHistory();
        billHistory.setBill(bill);
        billHistory.setPayingAmount(payingAmount);
        billHistory.setCreatedDate(bill.getCreatedDate());
        billHistory.setModifiedDate(bill.getModifiedDate());
        billHistoryRepository.save(billHistory);
    }

    private Double calculateTotalAmount(List<OrderDto> orderDtoList) {
        double totalAmount = 0.0;
        for (OrderDto orderDto : orderDtoList) {
            totalAmount += orderDto.getPrice() * orderDto.getQuantity();
        }
        return totalAmount;
    }

    private Order mapToEntity(OrderDto orderDto) {
        return objectMapper.convertValue(orderDto, Order.class);
    }

    private OrderDto mapOrderToDto(Order order) {
        return objectMapper.convertValue(order, OrderDto.class);
    }

    private BillDto mapBillToDto(Bill bill) {
        BillDto billDto = objectMapper.convertValue(bill, BillDto.class);
        billDto.setOrderDtos(bill.getOrders().stream().map(this::mapOrderToDto).collect(Collectors.toList()));
        return billDto;
    }


    public List<BillDto> getAllBills() {
        return billRepository.findAll().
                stream().map(this::mapBillToDto)
                .collect(Collectors.toList());
    }

    public List<BillDto> getBillsByWorkerId(Long workerId) {

        return billRepository.findAllByManagerId(workerId)
                .stream().map(this::mapBillToDto)
                .toList();
    }
}
