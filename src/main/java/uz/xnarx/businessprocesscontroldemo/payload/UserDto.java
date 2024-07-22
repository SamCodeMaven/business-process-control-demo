package uz.xnarx.businessprocesscontroldemo.payload;

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

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String address;

    private Role role;

    private Date createdDate;

    private boolean enabled;

}
