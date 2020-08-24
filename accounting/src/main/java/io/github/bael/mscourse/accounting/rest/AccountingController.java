package io.github.bael.mscourse.accounting.rest;

import io.github.bael.mscourse.accounting.service.AccountingAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountingController {

    private final AccountingAPI api;

    @GetMapping("/balance/{customerId}")
    public BigDecimal balance(@PathVariable String customerCode) {
        LocalDate date = LocalDate.now();
        return api.balance(customerCode, date);
    }
    
    @PostMapping("/payment/")
    public void registerPayment(@RequestBody PaymentRegistrationDTO payment) {
        api.registerPayment(payment.getPaymentId(), payment.getPaidSum(), payment.getPaidOn(), payment.getCustomerCode(), payment.getOrderCode());
//        PaymentRegistrationDTO dto = new PaymentRegistrationDTO();
//        dto.setCustomerCode("");
//        dto.setOrderCode("");
//        dto.setPaidOn(LocalDate.of(2020, 1, 1));
//        dto.setPaymentId(UUID.randomUUID().toString());
//        dto.setPaidSum(BigDecimal.valueOf(10000));
    }


}
