package com.example.consumer.domain;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
public class StatCounter {
    private final int WINDOW_SIZE = 60;
    private final Integer[] ySums;
    private final Double[] xSums;
    private final long[] times;
    private final int[] counts;

    public StatCounter() {
        xSums = new Double[WINDOW_SIZE];
        ySums = new Integer[WINDOW_SIZE];
        times = new long[WINDOW_SIZE];
        counts = new int[WINDOW_SIZE];
    }

    private boolean isExpired(long timestamp, long currentTime) {
        return currentTime > timestamp + WINDOW_SIZE * 1000;
    }

    public synchronized void add(long timestamp, Double x, Integer y) {
        long currentTime = System.currentTimeMillis();
        // if too late -> skipp this event
        if (isExpired(timestamp, currentTime)) {
            log.info("skin addition because {} is expired compare to {}", timestamp, currentTime);
            return;
        }
        int index = (int) timestamp % WINDOW_SIZE;
        if (times[index] != timestamp) {
            times[index] = timestamp;
            counts[index] = 0;
            xSums[index] = 0.0;
            ySums[index] = 0;
        }
        xSums[index] += x;
        ySums[index] += y;
        counts[index]++;
    }

    public synchronized Optional<Statistic> getStat() {
        long currentTime = System.currentTimeMillis();
        Double xSum = 0.0;
        Integer ySum = 0;
        int cnt = 0;
        for (int i = 0; i < times.length; i++) {
            if (!isExpired(times[i], currentTime)) {
                xSum += xSums[i];
                ySum += ySums[i];
                cnt += counts[i];
            }
        }
        log.info("get cnt: {}", cnt);
        if (cnt == 0) {
            return Optional.empty();
        }
        return Optional.of(Statistic.builder().total(cnt).xSum(xSum).xAvg(xSum / cnt).ySum(ySum).yAvg(ySum / cnt).build());
    }

}
