package io.github.bael.mscourse.accounting.data;

import io.github.bael.mscourse.accounting.entity.Invoice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InvoiceRepositoryTest {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Test
    void name() {
        List<Invoice> allActiveOverdue =
                invoiceRepository.findAllActiveOverdue(LocalDateTime.now());
        System.out.println(allActiveOverdue);

    }
}