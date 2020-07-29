package io.github.bael.mscourse.inventory.api;

import io.github.bael.mscourse.inventory.domain.RentPeriod;

import java.util.List;

public interface SKUApi {


    List<String> getFreeSKU(String productNumber, RentPeriod rentPeriod);

    boolean isAvailable(String SKU, RentPeriod rentPeriod);

    void reserve(String SKU, RentPeriod rentPeriod);

    void ship(String SKU);

    void accept(String SKU);
}
