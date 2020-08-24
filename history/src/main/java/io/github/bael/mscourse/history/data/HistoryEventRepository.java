package io.github.bael.mscourse.history.data;

import io.github.bael.mscourse.history.entity.HistoryEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HistoryEventRepository extends CrudRepository<HistoryEvent, Long>, JpaRepository<HistoryEvent, Long> {
}
