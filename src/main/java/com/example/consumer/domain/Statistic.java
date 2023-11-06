package com.example.consumer.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Statistic {
    Integer total;
    Double xSum;
    Double xAvg;
    Integer ySum;
    Integer yAvg;

    @Override
    public String toString() {
        return total + "," + xSum + "," + xAvg + "," + ySum + "," + yAvg;
    }
}
