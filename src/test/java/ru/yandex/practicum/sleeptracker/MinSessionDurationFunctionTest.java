package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.function.MinSessionDurationFunction;
import ru.yandex.practicum.sleeptracker.sleepsession.Quality;
import ru.yandex.practicum.sleeptracker.sleepsession.SleepingSession;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MinSessionDurationFunctionTest {
    private MinSessionDurationFunction function;

    @BeforeEach
    void setUp() {
        function = new MinSessionDurationFunction();
    }

    @Test
    public void testApplyEmptyListReturnsZero() {
        List<SleepingSession> sessions = new ArrayList<>();
        Object result = function.apply(sessions);
        assertEquals(0L, result);
    }

    @Test
    void testApplySessionsReturnsMinDuration() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 25, 23, 0),
                        LocalDateTime.of(2026, 1, 26, 8, 30),
                        Quality.GOOD),
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 28, 2, 0),
                        LocalDateTime.of(2026, 1, 28, 4, 15),
                        Quality.BAD),
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 28, 23, 0),
                        LocalDateTime.of(2026, 1, 29, 7, 0),
                        Quality.NORMAL)
        );
        Object result = function.apply(sessions);
        assertEquals(135L, result);
    }
}