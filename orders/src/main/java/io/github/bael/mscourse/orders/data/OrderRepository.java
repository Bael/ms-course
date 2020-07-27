package io.github.bael.mscourse.orders.data;

import io.github.bael.mscourse.orders.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {

}
