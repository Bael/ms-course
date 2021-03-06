package io.github.bael.mscourse.inventory.service;

import com.google.common.collect.Range;
import io.github.bael.mscourse.inventory.api.InventoryApi;
import io.github.bael.mscourse.inventory.bus.InventoryEventBus;
import io.github.bael.mscourse.inventory.data.OrderShipmentRepository;
import io.github.bael.mscourse.inventory.data.SKURepository;
import io.github.bael.mscourse.inventory.data.SchedulePeriodRepository;
import io.github.bael.mscourse.inventory.entity.*;
import io.github.bael.mscourse.shopdto.v1.InventoryReserveRequest;
import io.github.bael.mscourse.shopdto.v1.InventoryReserveResponse;
import io.github.bael.mscourse.shopdto.v1.ProductRentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InventoryService implements InventoryApi {

    private final SchedulePeriodRepository schedulePeriodRepository;
    private final SKURepository skuRepository;
    private final OrderShipmentRepository orderShipmentRepository;

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

    private final JdbcTemplate jdbcTemplate;

//    @Override
    public InventoryReserveResponse reserveOrderExp(InventoryReserveRequest request) {

        log.info("Reserve order requested!");

        Map<String, Long> allIds = new HashMap<>();
        for (ProductRentRequest rentRequest : request.getRequestDTOList()) {
            String query = "select top sku.id from sku sku left outer join schedule_period p on sku.id = p.sku_id where "
                    + "sku.product_code = ? and (p is null or (? <= period_start or ? >= period_finish )); ";
            List<Long> ids = jdbcTemplate.query(query, new Object[]{rentRequest.getProductCode(), rentRequest.getFinishOn(), rentRequest.getStartOn()}, (rs, rowNum) -> rs.getLong("id"));
            // мест нет
            if (ids.isEmpty()) {
                return InventoryReserveResponse.builder().orderReserved(false)
                        .orderCode(request.getOrderCode())
                        .build();
            }
            allIds.put(rentRequest.getProductCode(), ids.get(0));
        }

        OrderShipment shipment = new OrderShipment();
        shipment.setCreatedOn(LocalDateTime.now(ZoneOffset.UTC));
        shipment.setCustomerCode(request.getCustomerCode());
        shipment.setOrderCode(request.getOrderCode());
        shipment.setOrderShipmentStatus(OrderShipmentStatus.RESERVED);
        orderShipmentRepository.save(shipment);


        List<SchedulePeriod> schedulePeriods = schedulePeriods(request, shipment);
        schedulePeriodRepository.saveAll(schedulePeriods);
        for (ProductRentRequest rentRequest : request.getRequestDTOList()) {
            String query = "insert into schedule_period (sku_id, period_start, period_finish) values (?, ? , ?) ";
            Long id = allIds.get(rentRequest.getProductCode());
            jdbcTemplate.update(query, id, rentRequest.getStartOn(), rentRequest.getFinishOn());
        }

        log.info("Order reserved!");
        return InventoryReserveResponse.builder().orderReserved(true)
                .orderCode(request.getOrderCode())
                .build();
    }

    @Override
    public InventoryReserveResponse reserveOrder(InventoryReserveRequest request) {
        log.info("Reserve order requested!");
        // есть свободное место?
        if (!checkAvailabilityRequest(request)) {
            return InventoryReserveResponse.builder().orderReserved(false)
                    .orderCode(request.getOrderCode())
                    .build();
        }

        OrderShipment shipment = new OrderShipment();
        shipment.setCreatedOn(LocalDateTime.now(ZoneOffset.UTC));
        shipment.setCustomerCode(request.getCustomerCode());
        shipment.setOrderCode(request.getOrderCode());
        shipment.setOrderShipmentStatus(OrderShipmentStatus.RESERVED);
        orderShipmentRepository.save(shipment);

        List<SchedulePeriod> schedulePeriods = schedulePeriods(request, shipment);
        schedulePeriodRepository.saveAll(schedulePeriods);

        log.info("Order reserved!");
        return InventoryReserveResponse.builder().orderReserved(true)
                .orderCode(request.getOrderCode())
                .build();
    }

    private final InventoryEventBus inventoryEventBus;

    @Override
    public boolean shipOrder(String orderCode) {
        log.info("Отгружаем заказ (моментально)");
        OrderShipment shipment = orderShipmentRepository.findByOrderCode(orderCode).orElseThrow(RuntimeException::new);
        shipment.setOrderShipmentStatus(OrderShipmentStatus.DELIVERED);
        orderShipmentRepository.save(shipment);
        inventoryEventBus.sendOrderIsDeliveredEvent(orderCode, shipment.getCustomerCode(), LocalDateTime.now(ZoneOffset.UTC));
        log.info("Завершили");
        return true;
    }

    @Override
    public void freeOrder(String orderCode) {
        log.info("Очищаем резервы");
        OrderShipment shipment = orderShipmentRepository.findByOrderCode(orderCode).orElseThrow(RuntimeException::new);
        shipment.setOrderShipmentStatus(OrderShipmentStatus.CANCELED);
        orderShipmentRepository.save(shipment);

        // очищаем
        List<SchedulePeriod> allByOrderShipment = schedulePeriodRepository.findAllByOrderShipment(shipment);
        schedulePeriodRepository.deleteAll(allByOrderShipment);

        log.info("Очистка завершена");
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
        log.info("checkAvailabilityRequest started!");
        boolean b = request.getRequestDTOList().stream()
                .allMatch(req -> findFreeOnPeriod(req.getProductCode(), req.getStartOn(), req.getFinishOn()).isPresent());
        log.info("checkAvailabilityRequest finished!");
        return b;
    }


    private List<SKU> getByProductCode(String productCode) {
        return skuRepository.getAllSkuListByProductCode(productCode)
                .stream()
                .filter(sku -> sku.getStatus() == SKUStatus.AVAILABLE)
                .collect(Collectors.toList());
    }
}
