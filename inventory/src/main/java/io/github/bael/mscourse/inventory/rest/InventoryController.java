package io.github.bael.mscourse.inventory.rest;

import io.github.bael.mscourse.inventory.service.InventoryService;
import io.github.bael.mscourse.shopdto.v1.InventoryReserveRequest;
import io.github.bael.mscourse.shopdto.v1.InventoryReserveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/inventory/reserve")
    public InventoryReserveResponse requestAvailability(@RequestBody InventoryReserveRequest request) {
        return inventoryService.reserveOrder(request);
    }

}
