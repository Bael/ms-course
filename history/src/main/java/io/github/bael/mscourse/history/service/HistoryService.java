package io.github.bael.mscourse.history.service;

import io.github.bael.mscourse.history.data.HistoryEventRepository;
import io.github.bael.mscourse.history.data.HistoryLabelRepository;
import io.github.bael.mscourse.history.entity.HistoryEvent;
import io.github.bael.mscourse.history.entity.HistoryLabel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.websocket.server.ServerEndpoint;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryEventRepository historyEventRepository;
    private final HistoryLabelRepository historyLabelRepository;

    @Transactional
    public void saveEvent(String name, String payload, String eventType, Map<String, String> labels) {
        HistoryEvent historyEvent = new HistoryEvent();
        historyEvent.setCreatedOn(LocalDateTime.now());
        historyEvent.setEventType(eventType);
        historyEvent.setPayload(payload);
        historyEvent.setName(name);
        List<HistoryLabel> labelsList = new ArrayList<>();
        for(Map.Entry<String, String> entry : labels.entrySet()) {
            HistoryLabel label = new HistoryLabel();
            label.setEvent(historyEvent);
            label.setLabel(entry.getKey());
            label.setValue(entry.getValue());
            labelsList.add(label);
        }
        historyEventRepository.save(historyEvent);
        historyLabelRepository.saveAll(labelsList);
    }

    // TODO переписать
    public List<HistoryEvent> findByLabels(Map<String, String> labels) {
//        Map<String, Set<HistoryEvent>> events = new HashMap<>();
        Map<HistoryEvent, Set<String>> events = new HashMap<>();

        for(Map.Entry<String, String> entry : labels.entrySet()) {

            Set<HistoryEvent> eventByLabelSet = historyLabelRepository.findByLabelAndValue(entry.getKey(), entry.getValue());
            System.out.println(eventByLabelSet);

            eventByLabelSet.forEach(historyEvent -> {
                        Set<String> labelsSet = events.getOrDefault(historyEvent, new HashSet<>());
                        labelsSet.add(entry.getKey());
                        events.put(historyEvent, labelsSet);
                    }
                    );

        }

        Set<String> labelSet = labels.keySet();
        List<HistoryEvent> result = new ArrayList<>();
        for(Map.Entry<HistoryEvent, Set<String>> entry : events.entrySet()) {
            if (entry.getValue().containsAll(labelSet)) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    public List<HistoryEvent> findAll() {
        return historyEventRepository.findAll();
    }
}
