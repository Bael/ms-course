package io.github.bael.mscourse.orders.data;

import io.github.bael.mscourse.orders.entity.Order;
import io.github.bael.mscourse.orders.entity.OrderLine;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderLineRepository extends CrudRepository<OrderLine, Long> {

    List<OrderLine> findAllByOrder(Order order);
}
