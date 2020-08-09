package io.github.bael.mscourse.accounting.data;

import io.github.bael.mscourse.accounting.entity.Entry;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface EntryRepository extends CrudRepository<Entry, Long> {

    List<Entry> findAllByOrderId(UUID orderId);
    List<Entry> findAllByAccountId(UUID accountId);

}
