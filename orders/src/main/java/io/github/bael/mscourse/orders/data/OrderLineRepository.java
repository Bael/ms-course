package io.github.bael.mscourse.orders.data;

import io.github.bael.mscourse.orders.entity.OrderLine;
import org.springframework.data.repository.CrudRepository;

public interface OrderLineRepository extends CrudRepository<OrderLine, Long> {
}
