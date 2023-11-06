package com.example.consumer.web;


import com.example.consumer.domain.Statistic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.consumer.service.StatService;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping()
public class StatController {

    private final StatService statService;

    @Autowired
    public StatController(StatService statService) {
        this.statService = statService;
    }

    @GetMapping(path = "/stats", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getStat() {
        Optional<Statistic> statistic = statService.getStat();
        return ResponseEntity.ok(statistic.map(Statistic::toString).orElse(""));
    }

    @PostMapping("/add")
    public void addStat(@RequestParam long timestamp, @RequestParam Double x, @RequestParam Integer y) {
        statService.add(timestamp, x, y);
    }

    @PostMapping(path = "/event")
    public ResponseEntity<Void> create(@RequestBody String inputLine) {
        if (statService.add(inputLine)) {
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}



