package uz.xnarx.businessprocesscontroldemo.payload;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;

    private Long productId;

    private String productName;

    private Double price;

    private Double quantity;
}
