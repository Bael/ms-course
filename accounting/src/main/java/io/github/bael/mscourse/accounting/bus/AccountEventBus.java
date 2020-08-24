package io.github.bael.mscourse.accounting.bus;

import io.github.bael.mscourse.outbox.service.Outbox;
import io.github.bael.mscourse.shopdto.v1.event.OrderPaidEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountEventBus {
    private final Outbox outbox;
    public void sendOrderPaidEvent(String orderCode, String customerCode, BigDecimal paidSum, LocalDateTime paidOn) {
        OrderPaidEvent orderPaidEvent = OrderPaidEvent
                .builder().orderCode(orderCode)
                .customerCode(customerCode)
                .paidOn(paidOn)
                .totalAmount(paidSum)
                .build();
        outbox.addMessage(orderPaidEvent, "accounting", orderPaidEvent.getClass().getCanonicalName());
    }
}
