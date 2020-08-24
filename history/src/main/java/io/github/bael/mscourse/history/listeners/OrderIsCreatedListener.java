package io.github.bael.mscourse.history.listeners;

import io.github.bael.mscourse.history.service.HistoryService;
import io.github.bael.mscourse.outbox.event.OutboxEventListener;
import io.github.bael.mscourse.shopdto.v1.event.OrderCreatedEvent;
import io.github.bael.mscourse.shopdto.v1.event.OrderDeliveredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderIsCreatedListener implements OutboxEventListener<OrderCreatedEvent> {
    private final HistoryService historyService;

    @Override
    public boolean supports(String s) {
        return s.equals(OrderCreatedEvent.class.getCanonicalName());
    }

    @Override
    public void handle(OrderCreatedEvent event) {
        Map<String, String> labelMap = new HashMap<>();
        labelMap.put("CUSTOMER", event.getCustomerCode());
        labelMap.put("ORDER", event.getOrderCode());
        historyService.saveEvent("Заказ создан", event.toString(), event.getClass().getSimpleName(), labelMap);
    }
}
