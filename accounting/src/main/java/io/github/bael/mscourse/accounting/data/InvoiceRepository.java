package io.github.bael.mscourse.accounting.data;

import io.github.bael.mscourse.accounting.entity.Invoice;
import io.github.bael.mscourse.accounting.entity.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends CrudRepository<Invoice, Long>, JpaRepository<Invoice, Long> {
    @Query("select i from Invoice i where invoiceStatus = 'ACTIVE'  and deadline <= ?1")
    List<Invoice> findAllActiveOverdue(LocalDateTime dueDateTime);

    Optional<Invoice> findByOrderCode(String orderCode);

}
