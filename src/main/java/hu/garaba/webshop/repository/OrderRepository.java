package hu.garaba.webshop.repository;

import hu.garaba.webshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

// Spring Data JPA
public interface OrderRepository extends JpaRepository<Order, Long> {
}
