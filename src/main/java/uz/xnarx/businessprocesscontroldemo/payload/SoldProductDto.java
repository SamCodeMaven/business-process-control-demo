package uz.xnarx.businessprocesscontroldemo.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SoldProductDto {

    private  Long productId;

    private Double soldQuantity;
}
