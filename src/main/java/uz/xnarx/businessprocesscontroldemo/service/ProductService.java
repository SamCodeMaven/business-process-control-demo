package uz.xnarx.businessprocesscontroldemo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.xnarx.businessprocesscontroldemo.Entity.Product;
import uz.xnarx.businessprocesscontroldemo.exception.BadRequestException;
import uz.xnarx.businessprocesscontroldemo.exception.NotFoundException;
import uz.xnarx.businessprocesscontroldemo.payload.ProductDto;
import uz.xnarx.businessprocesscontroldemo.payload.SoldProductDto;
import uz.xnarx.businessprocesscontroldemo.repository.ProductRepository;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ObjectMapper objectMapper;

    private final ProductRepository productRepository;

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public ProductDto getProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        return objectMapper.convertValue(product, ProductDto.class);
    }

    @Transactional
    public void restockProduct(Long id, Double quantity) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        product.setQuantity(product.getQuantity() + quantity);
        product.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        productRepository.save(product);
    }

    @Transactional
    public void soldProduct(Long id, Double quantity) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        if (product.getQuantity()<quantity){
            throw new BadRequestException("Omborda mahsulot yetarlicha emas!");
        }
        product.setQuantity(product.getQuantity() - quantity);
        product.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        productRepository.save(product);
    }

    @Transactional
    public void saveProduct(ProductDto productDto) {
        Product product = new Product();
        if (productDto.getId() != null) {
            product = productRepository.findById(productDto.getId()).
                    orElseThrow(() -> new NotFoundException("Product not found with id: " + productDto.getId()));
        }
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setQuantity(productDto.getQuantity());
        product.setPrice(productDto.getPrice());
        product.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        productRepository.save(product);
    }


    public void decreaseProductQuantities(List<SoldProductDto> productQuantities) {
        for (SoldProductDto productQuantity : productQuantities) {
            Product product = productRepository.findById(productQuantity.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product not found with id " + productQuantity.getProductId()));
            product.setQuantity(product.getQuantity() - productQuantity.getSoldQuantity());
            productRepository.save(product);
        }
    }
    private ProductDto convertToDTO(Product product) {
        ProductDto productDTO = new ProductDto();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setQuantity(product.getQuantity());
        productDTO.setPrice(product.getPrice());
        productDTO.setUpdatedAt(product.getUpdatedAt());
        return productDTO;
    }

    @Transactional
    public List<ProductDto> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> objectMapper.convertValue(product,ProductDto.class)).toList();
    }
}
