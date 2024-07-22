package uz.xnarx.businessprocesscontroldemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.xnarx.businessprocesscontroldemo.Entity.Client;


import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAllByManagerId(Long managerId);

    List<Client> findAllByNameContainingIgnoreCase(String name);
}
