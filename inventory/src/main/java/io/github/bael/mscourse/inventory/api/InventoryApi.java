package io.github.bael.mscourse.inventory.api;

import io.github.bael.mscourse.inventory.domain.RentPeriod;
import io.github.bael.mscourse.shopdto.v1.InventoryReserveRequest;
import io.github.bael.mscourse.shopdto.v1.InventoryReserveResponse;

import java.util.List;

public interface InventoryApi {

//
//    List<String> getFreeSKU(String productNumber, RentPeriod rentPeriod);
//
//    boolean isAvailable(String SKU, RentPeriod rentPeriod);

    InventoryReserveResponse reserveOrder(InventoryReserveRequest request);

//
//    void reserve(String SKU, RentPeriod rentPeriod);
//
//    void ship(String SKU);
//
//    void accept(String SKU);
}
