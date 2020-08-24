package io.github.bael.mscourse.history.listeners;

import io.github.bael.mscourse.history.service.HistoryService;
import io.github.bael.mscourse.outbox.event.OutboxEventListener;
import io.github.bael.mscourse.shopdto.v1.event.OrderDeliveredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderIsDeliveredListener implements OutboxEventListener<OrderDeliveredEvent> {
    private final HistoryService historyService;

    @Override
    public boolean supports(String s) {
        return s.equals(OrderDeliveredEvent.class.getCanonicalName());
    }

    @Override
    public void handle(OrderDeliveredEvent event) {
        Map<String, String> labelMap = new HashMap<>();
        labelMap.put("CUSTOMER", event.getCustomerCode());
        labelMap.put("ORDER", event.getOrderCode());
        historyService.saveEvent("Заказ доставлен", event.toString(), event.getClass().getSimpleName(), labelMap);
    }
}
