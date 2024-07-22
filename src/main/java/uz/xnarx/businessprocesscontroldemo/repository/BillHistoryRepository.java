package uz.xnarx.businessprocesscontroldemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.xnarx.businessprocesscontroldemo.Entity.BillHistory;

public interface BillHistoryRepository extends JpaRepository<BillHistory, Long> {
}
