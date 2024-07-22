package uz.xnarx.businessprocesscontroldemo.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String Name;

    private String email;

    @NotBlank(message = "Phone number cannot be blank")
    private String phone;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private Double totalDebt;

    private Double totalCredit;

    private Long managerId;
}
