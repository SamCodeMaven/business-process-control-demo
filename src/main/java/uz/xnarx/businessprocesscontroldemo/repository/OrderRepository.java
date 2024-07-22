package uz.xnarx.businessprocesscontroldemo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.xnarx.businessprocesscontroldemo.Entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
