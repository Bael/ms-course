package io.github.bael.mscourse.outbox.data;

import io.github.bael.mscourse.outbox.entity.OutboxEntity;
import org.springframework.data.repository.CrudRepository;

public interface OutboxEntityRepository extends CrudRepository<OutboxEntity, Long>  {
}
