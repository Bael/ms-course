package io.github.bael.mscourse.accounting.service;

import io.github.bael.mscourse.accounting.data.EntryRepository;
import io.github.bael.mscourse.accounting.entity.Entry;
import io.github.bael.mscourse.accounting.entity.EntryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountingAPIService implements AccountingAPI {

    private final EntryRepository entryRepository;

    @Override
    public void chargeOrder(BigDecimal sum, LocalDate orderDate, UUID accountId, UUID orderId) {
        Entry entry = Entry.builder()
                // начисления идут с -, оплата и отмена начислений с +
                .amount(sum.negate())
                .entryDate(orderDate)
                .entryType(EntryType.INVOICE)
                .createdOn(LocalDateTime.now(ZoneOffset.UTC))
                .orderId(orderId)
                .accountId(accountId)
                .build();
        entryRepository.save(entry);
    }

    @Override
    public void dischargeOrder(BigDecimal sum, LocalDate reversalDate, UUID accountId, UUID orderId) {
        Entry entry = Entry.builder()
                // отмена начислений с +
                .amount(sum)
                .entryDate(reversalDate)
                .entryType(EntryType.INVOICE_REVERSAL)
                .createdOn(LocalDateTime.now(ZoneOffset.UTC))
                .orderId(orderId)
                .accountId(accountId)
                .build();
        entryRepository.save(entry);
    }

    @Override
    public void registerPayment(BigDecimal sum, LocalDate paymentDate, UUID accountId, UUID orderId) {
        Entry entry = Entry.builder()
                .amount(sum)
                .entryDate(paymentDate)
                .entryType(EntryType.PAYMENT)
                .createdOn(LocalDateTime.now(ZoneOffset.UTC))
                .orderId(orderId)
                .accountId(accountId)
                .build();
        entryRepository.save(entry);
    }

    @Override
    public void moneyBack(BigDecimal sum, LocalDate operationDate, UUID accountId, UUID orderId) {
        Entry entry = Entry.builder()
                .amount(sum.negate())
                .orderId(orderId)
                .entryDate(operationDate)
                .entryType(EntryType.PAYMENT_RETURN)
                .createdOn(LocalDateTime.now(ZoneOffset.UTC))
                .accountId(accountId)
                .build();
        entryRepository.save(entry);
    }

    @Override
    public BigDecimal balance(UUID accountId, LocalDate queryDate) {
        return entryRepository.findAllByAccountId(accountId).stream()
                .filter(entry -> !entry.getEntryDate().isAfter(queryDate)).map(Entry::getAmount)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal balance(UUID accountId, UUID orderId, LocalDate queryDate) {
        return entryRepository.findAllByOrderId(orderId).stream()
                .filter(entry -> !entry.getEntryDate().isAfter(queryDate)).map(Entry::getAmount)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }
}
