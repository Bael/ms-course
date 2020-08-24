package io.github.bael.mscourse.history.listeners;

import io.github.bael.mscourse.history.service.HistoryService;
import io.github.bael.mscourse.outbox.event.OutboxEventListener;
import io.github.bael.mscourse.shopdto.v1.event.OrderDeliveredEvent;
import io.github.bael.mscourse.shopdto.v1.event.OrderPaidEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderIsPaidListener implements OutboxEventListener<OrderPaidEvent> {
    private final HistoryService historyService;

    @Override
    public boolean supports(String s) {
        return s.equals(OrderPaidEvent.class.getCanonicalName());
    }

    @Override
    public void handle(OrderPaidEvent event) {
        Map<String, String> labelMap = new HashMap<>();
        labelMap.put("CUSTOMER", event.getCustomerCode());
        labelMap.put("ORDER", event.getOrderCode());
        historyService.saveEvent("Заказ оплачен", event.toString(), event.getClass().getSimpleName(), labelMap);
    }
}
