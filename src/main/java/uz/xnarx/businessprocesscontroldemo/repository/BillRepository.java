package uz.xnarx.businessprocesscontroldemo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.xnarx.businessprocesscontroldemo.Entity.Bill;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findAllByManagerId(Long id);
}
