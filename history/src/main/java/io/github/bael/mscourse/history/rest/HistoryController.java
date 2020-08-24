package io.github.bael.mscourse.history.rest;

import io.github.bael.mscourse.history.entity.HistoryEvent;
import io.github.bael.mscourse.history.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/")
    public List<HistoryDTO> getEvents(@RequestParam(required = false) Map<String,String> allParams) {
        return historyService.findByLabels(allParams).stream()
                .sorted(Comparator.comparing(HistoryEvent::getCreatedOn))
                .map(HistoryDTO::of).collect(Collectors.toList());
    }

    @GetMapping("/all")
    public List<HistoryDTO> getAllEvents() {
        return historyService.findAll().stream().map(HistoryDTO::of).collect(Collectors.toList());
    }
}
