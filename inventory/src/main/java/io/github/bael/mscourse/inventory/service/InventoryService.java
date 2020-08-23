package io.github.bael.mscourse.inventory.service;

import com.google.common.collect.Range;
import io.github.bael.mscourse.inventory.api.InventoryApi;
import io.github.bael.mscourse.inventory.data.OrderShipmentRepository;
import io.github.bael.mscourse.inventory.data.SKURepository;
import io.github.bael.mscourse.inventory.data.SchedulePeriodRepository;
import io.github.bael.mscourse.inventory.entity.*;
import io.github.bael.mscourse.shopdto.v1.InventoryReserveRequest;
import io.github.bael.mscourse.shopdto.v1.InventoryReserveResponse;
import io.github.bael.mscourse.shopdto.v1.ProductRentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService implements InventoryApi {

    private final SchedulePeriodRepository schedulePeriodRepository;
    private final SKURepository skuRepository;
    private final OrderShipmentRepository orderShipmentRepository;

//    @Override
//    public List<String> getFreeSKU(String productNumber, RentPeriod rentPeriod) {
//        return skuRepository.getAllSkuListByProductNumber(productNumber)
//                .stream()
//                .filter(sku -> SKUStatus.AVAILABLE == sku.getStatus())
//                .filter(sku -> isFree(sku, rentPeriod))
//                .map(SKU::getCode)
//                .collect(Collectors.toList());
//
//    }


//
//    @Override
//    public boolean isAvailable(String sku, RentPeriod rentPeriod) {
//        return isFree(getItemByCode(sku), rentPeriod);
//    }
//
//    private SKU getItemByCode(String code) {
//        SKU item = skuRepository.findByCode(code).orElseThrow(() ->
//                new ObjectNotFoundException("SKU not found:", code));
//        return item;
//    }

//
//    @Override
//    public void reserve(String skuCode, RentPeriod rentPeriod) {
//        SKU item = getItemByCode(skuCode);
//        if (item.getStatus() != SKUStatus.AVAILABLE) {
//            throw new IllegalStateException("Wrong state of unit!" + item.getStatus());
//        }
//
//        if (!isFree(item, rentPeriod)) {
//            throw new IllegalStateException("Unit is not free for period! Code:" + item.getCode() + " period: " + rentPeriod.getPeriod());
//        }
//        item.setStatus(SKUStatus.RESERVED);
//        SchedulePeriod period = new SchedulePeriod();
//        period.setItem(item);
//        period.setPeriodStart(rentPeriod.getPeriod().lowerEndpoint());
//        period.setPeriodFinish(rentPeriod.getPeriod().upperEndpoint());
//
//        schedulePeriodRepository.save(period);
//        skuRepository.save(item);
//
//    }

//    @Override
//    public void ship(String skuCode) {
//        SKU item = getItemByCode(skuCode);
//        if (item.getStatus() != SKUStatus.RESERVED) {
//            throw new IllegalStateException("Wrong state of unit!" + item.getStatus());
//        }
//        item.setStatus(SKUStatus.OUT);
//        skuRepository.save(item);
//    }
//
//    @Override
//    public void accept(String skuCode) {
//        SKU item = getItemByCode(skuCode);
//        if (item.getStatus() != SKUStatus.OUT) {
//            throw new IllegalStateException("Wrong state of unit!" + item.getStatus());
//        }
//        item.setStatus(SKUStatus.AVAILABLE);
//        skuRepository.save(item);
//    }

    /**
     * Не занят ли искомый артикул в указанном периоде?
     *
     * @param sku
     * @param startOn
     * @param finishOn
     * @return
     */
    private boolean isFree(SKU sku, LocalDate startOn, LocalDate finishOn) {
        Range<LocalDate> range = Range.closed(startOn, finishOn);
        return sku.getStatus() == SKUStatus.AVAILABLE
                && schedulePeriodRepository.findAllByItem(sku)
                .stream()
                .noneMatch(period -> range.isConnected(Range.closed(period.getPeriodStart(), period.getPeriodFinish())));
    }

    @Override
    public synchronized InventoryReserveResponse reserveOrder(InventoryReserveRequest request) {

        // есть свободное место?
        if (!checkAvailabilityRequest(request)) {
            return InventoryReserveResponse.builder().orderReserved(false)
                    .orderCode(request.getOrderCode())
                    .build();
        }

        OrderShipment shipment = new OrderShipment();
        shipment.setCreatedOn(LocalDateTime.now(ZoneOffset.UTC));
        shipment.setCustomerCode(request.getCustomerCode());
//        shipment.setCustomerName(request.getCustomerCode());
        shipment.setOrderCode(request.getOrderCode());
        shipment.setOrderShipmentStatus(OrderShipmentStatus.RESERVED);
        orderShipmentRepository.save(shipment);

        List<SchedulePeriod> schedulePeriods = schedulePeriods(request, shipment);
        schedulePeriodRepository.saveAll(schedulePeriods);

        return InventoryReserveResponse.builder().orderReserved(true)
                .orderCode(request.getOrderCode())
                .build();
    }

    private List<SchedulePeriod> schedulePeriods(InventoryReserveRequest request, OrderShipment shipment) {
        List<SchedulePeriod> list = new ArrayList<>();
        for (ProductRentRequest lineRequest : request.getRequestDTOList()) {
            SKU freeOnPeriod = findFreeOnPeriod(lineRequest.getProductCode(),
                    lineRequest.getStartOn(), lineRequest.getFinishOn()).orElseThrow(RuntimeException::new);
            SchedulePeriod period = new SchedulePeriod();
            period.setItem(freeOnPeriod);
            period.setOrderShipment(shipment);
            period.setPeriodStart(lineRequest.getStartOn());
            period.setPeriodFinish(lineRequest.getFinishOn());
            list.add(period);
        }
        return list;
    }

    private Optional<SKU> findFreeOnPeriod(String productCode, LocalDate startOn, LocalDate finishOn) {
        List<SKU> skuListByProduct = getByProductCode(productCode);
        for (SKU sku : skuListByProduct) {
            if (isFree(sku, startOn, finishOn)) {
                return Optional.of(sku);
            }
        }
        return Optional.empty();
    }

    private boolean checkAvailabilityRequest(InventoryReserveRequest request) {
        return request.getRequestDTOList().stream()
                .allMatch(req -> findFreeOnPeriod(req.getProductCode(), req.getStartOn(), req.getFinishOn()).isPresent());
    }


    private List<SKU> getByProductCode(String productCode) {
        return skuRepository.getAllSkuListByProductCode(productCode)
                .stream()
                .filter(sku -> sku.getStatus() == SKUStatus.AVAILABLE)
                .collect(Collectors.toList());
    }
}
