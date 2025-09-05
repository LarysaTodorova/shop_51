package ait.shop.repository;

import ait.shop.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Ни одного метода внутри нет
}
