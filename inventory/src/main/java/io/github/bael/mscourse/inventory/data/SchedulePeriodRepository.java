package io.github.bael.mscourse.inventory.data;

import io.github.bael.mscourse.inventory.entity.OrderShipment;
import io.github.bael.mscourse.inventory.entity.SKU;
import io.github.bael.mscourse.inventory.entity.SchedulePeriod;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SchedulePeriodRepository extends CrudRepository<SchedulePeriod, Long> {
    List<SchedulePeriod> findAllByItem(SKU sku);
    List<SchedulePeriod> findAllByOrderShipment(OrderShipment shipment);
}
