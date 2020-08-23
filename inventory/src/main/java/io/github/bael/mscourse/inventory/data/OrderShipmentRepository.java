package io.github.bael.mscourse.inventory.data;

import io.github.bael.mscourse.inventory.entity.OrderShipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderShipmentRepository extends CrudRepository<OrderShipment, Long>, JpaRepository<OrderShipment, Long> {

    Optional<OrderShipment> findByOrderCode(String orderCode);
}
