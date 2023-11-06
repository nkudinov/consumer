package com.example.consumer.repository;

import com.example.consumer.domain.StatCounter;
import com.example.consumer.domain.Statistic;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StatRepository {
    public Optional<Statistic> getStat() {
        return statCounter.getStat();
    }

    public StatRepository() {
        this.statCounter = new StatCounter();
    }

    StatCounter statCounter;

    public void add(long timestamp, Double x, Integer y) {
        statCounter.add(timestamp, x, y);
    }
}
