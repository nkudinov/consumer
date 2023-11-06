import com.example.consumer.domain.StatCounter;
import com.example.consumer.domain.Statistic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class StatCounterTest {

    private StatCounter statCounter;

    @BeforeEach
    void setUp() {
        statCounter = new StatCounter();
    }

    @Test
    void testAdd() {
        long timestamp = System.currentTimeMillis();
        Double x = 10.0;
        Integer y = 5;

        statCounter.add(timestamp, x, y);

        // Check that the values were added successfully
        Optional<Statistic> statistic = statCounter.getStat();
        assertFalse(statistic.isEmpty());
        assertEquals(1, statistic.get().getTotal());
        assertEquals(x, statistic.get().getXSum());
        assertEquals(y, statistic.get().getYSum());
    }

    @Test
    void testAddOutOfWindow() {
        long timestamp = System.currentTimeMillis() - 61_000; // More than WINDOW_SIZE milliseconds ago
        Double x = 10.0;
        Integer y = 5;

        statCounter.add(timestamp, x, y);

        // Check that the values were not added because they are out of the window
        Optional<Statistic> statistic = statCounter.getStat();
        assertTrue(statistic.isEmpty());
    }

    @Test
    void testGetStatNoData() {
        // Check that when no data is added, it returns an empty Optional
        Optional<Statistic> statistic = statCounter.getStat();
        assertTrue(statistic.isEmpty());
    }

    @Test
    void testGetStatWithDataOutOfWindow() {
        // Add some data that is out of the window
        long timestamp = System.currentTimeMillis() - 61_000; // More than WINDOW_SIZE milliseconds ago
        Double x = 10.0;
        Integer y = 5;

        statCounter.add(timestamp, x, y);

        // Check that when no valid data is within the window, it returns an empty Optional
        Optional<Statistic> statistic = statCounter.getStat();
        assertTrue(statistic.isEmpty());
    }
}
