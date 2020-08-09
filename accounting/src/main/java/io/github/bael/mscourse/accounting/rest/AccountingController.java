package io.github.bael.mscourse.accounting.rest;

import io.github.bael.mscourse.accounting.service.AccountingAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AccountingController {

    private final AccountingAPI api;

    @GetMapping("/balance/{customerId}")
    public BigDecimal balance(@PathVariable UUID customerId) {
        LocalDate date = LocalDate.now();
        return api.balance(customerId, date);
    }


}
