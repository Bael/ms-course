package io.github.bael.mscourse.orders.data;

import io.github.bael.mscourse.orders.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long>, JpaRepository<Order, Long> {
    Optional<Order> findByOrderCode(String orderCode);

}
