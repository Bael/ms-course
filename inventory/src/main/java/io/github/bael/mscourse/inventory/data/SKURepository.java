package io.github.bael.mscourse.inventory.data;

import io.github.bael.mscourse.inventory.entity.SKU;
import io.github.bael.mscourse.inventory.entity.SchedulePeriod;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SKURepository extends CrudRepository<SKU, Long> {
    List<SKU> getAllSkuListByProductNumber(String productNumber);

    Optional<SKU> findByCode(String sku);
}
