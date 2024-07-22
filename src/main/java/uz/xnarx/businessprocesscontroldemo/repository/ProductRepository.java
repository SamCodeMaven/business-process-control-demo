package uz.xnarx.businessprocesscontroldemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.xnarx.businessprocesscontroldemo.Entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
}
