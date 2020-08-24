package io.github.bael.mscourse.history.data;

import io.github.bael.mscourse.history.entity.HistoryEvent;
import io.github.bael.mscourse.history.entity.HistoryLabel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface HistoryLabelRepository extends CrudRepository<HistoryLabel, Long>, JpaSpecificationExecutor<HistoryLabel> {
    List<HistoryLabel> findAllByLabelAndValue(String label, String value);

    @Query("select he from HistoryLabel l join l.event he where l.label = ?1 and l.value = ?2")
    Set<HistoryEvent> findByLabelAndValue(String label, String value);


}
