package uz.xnarx.businessprocesscontroldemo.payload;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillDto {

    private Long id;

    private Long workerId;

    private Long clientId;

    private Double totalPrice;
    private Double paidAmount;
    private Double borrowedAmount;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    private Boolean checked;


    private List<OrderDto> orderDtos;
}