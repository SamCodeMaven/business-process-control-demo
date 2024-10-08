package uz.xnarx.businessprocesscontroldemo.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.xnarx.businessprocesscontroldemo.Entity.Role;


import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    private String firstName;

    private String lastName;

    @NotBlank(message = "email cannot be blank")
    private String email;

    @NotBlank(message = "phone number cannot be blank")
    private String phone;

    private String address;

    @NotBlank(message = "Role cannot be blank")
    private Role role;

    private Date createdDate;

    private boolean enabled;

}
