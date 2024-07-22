package uz.xnarx.businessprocesscontroldemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import uz.xnarx.businessprocesscontroldemo.payload.UserDto;
import uz.xnarx.businessprocesscontroldemo.service.UserService;

import static uz.xnarx.businessprocesscontroldemo.Entity.Role.ADMIN;
import static uz.xnarx.businessprocesscontroldemo.Entity.Role.MANAGER;

@SpringBootApplication
public class BusinessProcessControlDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessProcessControlDemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            UserService service
    ) {
        return args -> {
            var admin = UserDto.builder()
                    .firstName("Admin")
                    .lastName("Admin1")
                    .email("admin@mail.com")
                    .password("password")
                    .phone("+998901001000")
                    .address("Tashkent")
                    .role(ADMIN)
                    .build();
            System.out.println("Admin token: " + service.registerUser(admin).getAccessToken());
            System.out.println(admin.toString());

            var manager = UserDto.builder()
                    .firstName("Manager")
                    .lastName("Manager1")
                    .email("manager@mail.com")
                    .password("manager1")
                    .phone("+998901001010")
                    .address("Tashkent")
                    .role(MANAGER)
                    .build();
            System.out.println("Manager token: " + service.registerUser(manager).getAccessToken());

        };
    }

}
