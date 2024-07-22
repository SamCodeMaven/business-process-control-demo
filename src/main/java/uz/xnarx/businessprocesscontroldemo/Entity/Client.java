package uz.xnarx.businessprocesscontroldemo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "^[+][9][9][8][0-9]{9}$", message = "Phone number must be 13 digits.")
    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false, unique = true)
    private String address;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private Double totalDebt;

    private Double totalCredit;

//    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Bill> bills;


    private Long managerId;
}
