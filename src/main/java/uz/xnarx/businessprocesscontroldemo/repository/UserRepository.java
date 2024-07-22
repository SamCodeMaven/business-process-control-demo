package uz.xnarx.businessprocesscontroldemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.xnarx.businessprocesscontroldemo.Entity.Users;


import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {


    Optional<Users> findByEmail(String email);

    Users findByPhone(String phone);
}
