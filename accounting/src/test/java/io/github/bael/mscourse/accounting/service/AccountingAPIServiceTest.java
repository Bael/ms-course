package io.github.bael.mscourse.accounting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
//@ExtendWith(MockitoExtension.class)
class AccountingAPIServiceTest {
//
//    @Autowired
//    private AccountingAPI accountingAPI;
//
//    private final UUID accountId = UUID.randomUUID();
//    private final UUID orderId = UUID.randomUUID();
//
//    @Test
//    @Transactional
//    void charge() {
//        LocalDate date = LocalDate.now();
//        assertEquals(BigDecimal.ZERO, accountingAPI.balance(accountId, date));
//        accountingAPI.chargeOrder(BigDecimal.valueOf(4500L), date, accountId, orderId);
//        assertEquals(BigDecimal.valueOf(4500L).negate(), accountingAPI.balance(accountId, date));
//    }
//
//    @Test
//    @Transactional
//    void chargeAndPay() {
//        LocalDate date = LocalDate.now();
//        assertEquals(BigDecimal.ZERO, accountingAPI.balance(accountId, date));
//        accountingAPI.chargeOrder(BigDecimal.valueOf(4500L), date, accountId, orderId);
//        assertEquals(BigDecimal.valueOf(4500L).negate(), accountingAPI.balance(accountId, date));
//        accountingAPI.registerPayment(payment.getPaymentId(), BigDecimal.valueOf(4000L), date, accountId, orderId);
//
//        assertEquals(BigDecimal.valueOf(500L).negate(), accountingAPI.balance(accountId, orderId, date));
//
//        // даем скидку за помятость товара
//        accountingAPI.dischargeOrder(BigDecimal.valueOf(500L), date, accountId, orderId);
//        assertEquals(BigDecimal.ZERO, accountingAPI.balance(accountId, date));
//    }

}