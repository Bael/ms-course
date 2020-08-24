package io.github.bael.mscourse.accounting.bus;

import io.github.bael.mscourse.outbox.service.Outbox;
import io.github.bael.mscourse.shopdto.v1.event.OrderNotPaidEvent;
import io.github.bael.mscourse.shopdto.v1.event.OrderPaidEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    public void sendOrderNotPaidEvent(BigDecimal amount, LocalDateTime deadline, String customerCode, String orderCode) {
        OrderNotPaidEvent orderNotPaidEvent = OrderNotPaidEvent
                .builder().orderCode(orderCode)
                .customerCode(customerCode)
                .overdueOn(deadline)
                .totalAmount(amount)
                .build();
        outbox.addMessage(orderNotPaidEvent, "accounting", orderNotPaidEvent.getClass().getCanonicalName());
    }
}
