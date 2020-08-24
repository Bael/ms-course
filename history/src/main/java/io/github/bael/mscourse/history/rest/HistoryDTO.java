package io.github.bael.mscourse.history.rest;

import io.github.bael.mscourse.history.entity.HistoryEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class HistoryDTO {
    private Long id;
    private String eventType;
    private String changeBy;
    private String payload;
    private String name;
    private LocalDateTime createdOn;

    public static HistoryDTO of(HistoryEvent historyEvent) {
        return HistoryDTO.builder()
                .id(historyEvent.getId())
                .createdOn(historyEvent.getCreatedOn())
                .eventType(historyEvent.getEventType())
                .name(historyEvent.getName())
                .payload(historyEvent.getPayload())
                .changeBy(historyEvent.getChangeBy())
                .build();
    }
}
