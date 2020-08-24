package io.github.bael.mscourse.accounting.data;

import io.github.bael.mscourse.accounting.entity.Entry;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EntryRepository extends CrudRepository<Entry, Long> {

    List<Entry> findAllByOrderCode(String orderCode);
    List<Entry> findAllByCustomerCode(String customerCode);
    Optional<Entry> findByEntryKey(String entryKey);

}
