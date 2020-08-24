package io.github.bael.mscourse.history.listeners;

import io.github.bael.mscourse.history.service.HistoryService;
import io.github.bael.mscourse.outbox.event.OutboxEventListener;
import io.github.bael.mscourse.shopdto.v1.event.OrderNotPaidEvent;
import io.github.bael.mscourse.shopdto.v1.event.OrderPaidEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderIsNotPaidListener implements OutboxEventListener<OrderNotPaidEvent> {
    private final HistoryService historyService;

    @Override
    public boolean supports(String s) {
        return s.equals(OrderNotPaidEvent.class.getCanonicalName());
    }

    @Override
    public void handle(OrderNotPaidEvent event) {
        Map<String, String> labelMap = new HashMap<>();
        labelMap.put("CUSTOMER", event.getCustomerCode());
        labelMap.put("ORDER", event.getOrderCode());
        historyService.saveEvent("Заказ не оплачен вовремя. Отмена", event.toString(), event.getClass().getSimpleName(), labelMap);
    }
}
