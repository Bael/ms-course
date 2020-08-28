package io.github.bael.mscourse.accounting.service;

import io.github.bael.mscourse.accounting.bus.AccountEventBus;
import io.github.bael.mscourse.accounting.data.EntryRepository;
import io.github.bael.mscourse.accounting.data.InvoiceRepository;
import io.github.bael.mscourse.accounting.entity.Entry;
import io.github.bael.mscourse.accounting.entity.EntryType;
import io.github.bael.mscourse.accounting.entity.Invoice;
import io.github.bael.mscourse.accounting.entity.InvoiceStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountingAPIService implements AccountingAPI {

    private final EntryRepository entryRepository;
    private final InvoiceRepository invoiceRepository;
    private final AccountEventBus accountEventBus;
    @Value("${deadline.interval}")
    private int minutesInterval;

    @Override
    public void chargeOrder(BigDecimal sum, LocalDate orderDate, String customerCode, String orderCode) {

        Invoice invoice = new Invoice();
        invoice.setAmount(sum);
        invoice.setCreatedOn(LocalDateTime.now(ZoneOffset.UTC));
        invoice.setDeadline(LocalDateTime.now(ZoneOffset.UTC).plusMinutes(minutesInterval));
        invoice.setInvoiceStatus(InvoiceStatus.ACTIVE);
        invoice.setOrderCode(orderCode);
        invoice.setCustomerCode(customerCode);
        invoiceRepository.save(invoice);

        Entry entry = Entry.builder()
                // начисления идут с -, оплата и отмена начислений с +
                .amount(sum.negate())
                .entryDate(orderDate)
                .entryType(EntryType.INVOICE)
                .createdOn(LocalDateTime.now(ZoneOffset.UTC))
                .orderCode(orderCode)
                .customerCode(customerCode)
                .build();
        entryRepository.save(entry);
    }

    @Scheduled(fixedDelay = 5000)
    public void poll() {
        invoiceRepository.findAllActiveOverdue(LocalDateTime.now(ZoneOffset.UTC))
                .forEach(this::dischargeInvoice);

    }

    public void dischargeInvoice(Invoice invoice) {
        if (balance(invoice.getCustomerCode(), invoice.getOrderCode(), LocalDate.now(ZoneOffset.UTC)).compareTo(BigDecimal.ZERO) < 0) {
            invoice.setInvoiceStatus(InvoiceStatus.CANCELLED);
            dischargeOrder(invoice.getAmount(), LocalDate.now(ZoneOffset.UTC),
                    invoice.getCustomerCode(), invoice.getOrderCode());

            accountEventBus.sendOrderNotPaidEvent(invoice.getAmount(), LocalDateTime.now(ZoneOffset.UTC),
                    invoice.getCustomerCode(), invoice.getOrderCode());

        }

    }

    @Override
    public void dischargeOrder(BigDecimal sum, LocalDate reversalDate, String customerCode, String orderCode) {
        Entry entry = Entry.builder()
                // отмена начислений с +
                .amount(sum)
                .entryDate(reversalDate)
                .entryType(EntryType.INVOICE_REVERSAL)
                .createdOn(LocalDateTime.now(ZoneOffset.UTC))
                .orderCode(orderCode)
                .customerCode(customerCode)
                .build();
        entryRepository.save(entry);
    }

    @Override
    public void registerPayment(String paymentId, BigDecimal sum, LocalDate paymentDate, String customerCode, String orderCode) {
        log.info("регистрация платежа");

        if (entryRepository.findByEntryKey(paymentId).isEmpty()) {
            Entry entry = Entry.builder()
                    .entryKey(paymentId)
                    .amount(sum)
                    .entryDate(paymentDate)
                    .entryType(EntryType.PAYMENT)
                    .createdOn(LocalDateTime.now(ZoneOffset.UTC))
                    .orderCode(orderCode)
                    .customerCode(customerCode)
                    .build();
            entryRepository.save(entry);

            // если заказ оплачен
            LocalDate today = LocalDate.now(ZoneOffset.UTC);
            if (balance(customerCode, orderCode, today).compareTo(BigDecimal.ZERO) >= 0) {
                BigDecimal paidSum = entryRepository.findAllByOrderCode(orderCode).stream()
                        .filter(paymentEntry -> !paymentEntry.getEntryDate().isAfter(today))
                        .filter(paymentEntry1 -> paymentEntry1.getEntryType().equals(EntryType.PAYMENT))
                        .map(Entry::getAmount)
                        .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
                invoiceRepository.findByOrderCode(orderCode)
                        .map(invoice -> {
                            invoice.setInvoiceStatus(InvoiceStatus.PAID);
                            invoiceRepository.save(invoice);
                            return invoice;
                        });
                accountEventBus.sendOrderPaidEvent(orderCode, customerCode, paidSum, LocalDateTime.now(ZoneOffset.UTC));
            }
        }

        log.info("регистрация платежа завершена");
    }

    @Override
    public void moneyBack(BigDecimal sum, LocalDate operationDate, String customerCode, String orderCode) {
        Entry entry = Entry.builder()
                .amount(sum.negate())
                .orderCode(orderCode)
                .customerCode(customerCode)
                .entryDate(operationDate)
                .entryType(EntryType.PAYMENT_RETURN)
                .createdOn(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        entryRepository.save(entry);
    }

    @Override
    public BigDecimal balance(String customerCode, LocalDate queryDate) {
        return entryRepository.findAllByCustomerCode(customerCode).stream()
                .filter(entry -> !entry.getEntryDate().isAfter(queryDate)).map(Entry::getAmount)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal balance(String customerCode, String orderCode, LocalDate queryDate) {
        return entryRepository.findAllByOrderCode(orderCode).stream()
//                .filter(entry -> !entry.getEntryDate().isAfter(queryDate))
                .map(Entry::getAmount)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }
}
