package com.example.consumer.service;

import com.example.consumer.domain.Statistic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.consumer.repository.StatRepository;

import java.util.Optional;


@Slf4j
@Service
public class StatService {
    public Optional<Statistic> getStat() {
        return repository.getStat();
    }

    public void add(long timestamp, Double x, Integer y) {
        repository.add(timestamp, x, y);
    }

    public boolean add(String line) {
        String[] parts = line.split(",");
        if (parts.length != 3) {
            return false;
        }
        try {
            long timestamp = Long.parseLong(parts[0]);
            Double x = Double.parseDouble(parts[1]);
            Integer y = Integer.parseInt(parts[2]);
            add(timestamp, x, y);
            return true;
        } catch (Exception e) {
            log.info("can not parse a line: {}", line, e);
            return false;
        }
    }

    private StatRepository repository;

    public StatService(StatRepository repository) {
        this.repository = repository;
    }

}
