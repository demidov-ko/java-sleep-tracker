package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.function.AverageSessionDurationFunction;
import ru.yandex.practicum.sleeptracker.sleepsession.Quality;
import ru.yandex.practicum.sleeptracker.sleepsession.SleepingSession;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AverageSessionDurationFunctionTest {
    private AverageSessionDurationFunction function;

    @BeforeEach
    void setUp() {
        function = new AverageSessionDurationFunction();
    }

    @Test
    public void testApplyEmptyListReturnsZero() {
        List<SleepingSession> emptySessions = new ArrayList<>();

        double result = (double) function.apply(emptySessions);
        assertEquals(0.0, result, 0.0);
    }

    @Test
    void testApplySingleSession60Minutes() {
        SleepingSession session = new SleepingSession(
                LocalDateTime.of(2023, 10, 1, 22, 0),
                LocalDateTime.of(2023, 10, 1, 23, 0),
                Quality.NORMAL
        );
        List<SleepingSession> sessions = List.of(session);

        Object result = function.apply(sessions);
        assertEquals("60.00", result);
    }

    @Test
    void testApplyTwoSessionsDifferentDurations() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 25, 23, 0),
                        LocalDateTime.of(2026, 1, 26, 8, 30),
                        Quality.GOOD),
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 28, 2, 0),
                        LocalDateTime.of(2026, 1, 28, 4, 15),
                        Quality.BAD)
        );
        Object result = function.apply(sessions);
        assertEquals("352.50", result);
    }
}