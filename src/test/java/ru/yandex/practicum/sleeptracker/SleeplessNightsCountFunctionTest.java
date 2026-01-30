package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.function.SleeplessNightsCountFunction;
import ru.yandex.practicum.sleeptracker.sleepsession.Quality;
import ru.yandex.practicum.sleeptracker.sleepsession.SleepingSession;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SleeplessNightsCountFunctionTest {
    private SleeplessNightsCountFunction function;

    @BeforeEach
    void setUp() {
        function = new SleeplessNightsCountFunction();
    }

    @Test
    public void testApplyEmptyListReturnsZero() {
        List<SleepingSession> sessions = new ArrayList<>();
        Object result = function.apply(sessions);
        assertEquals(0L, result);
    }

    @Test
    void testApplySessionsOneSleeplessNight() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 26, 23, 0),
                        LocalDateTime.of(2026, 1, 27, 8, 30),
                        Quality.GOOD),
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 28, 9, 0),
                        LocalDateTime.of(2026, 1, 28, 13, 30),
                        Quality.BAD),
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 28, 22, 0),
                        LocalDateTime.of(2026, 1, 29, 4, 30),
                        Quality.NORMAL)
        );
        Object result = function.apply(sessions);
        assertEquals(1L, result);
    }

    @Test
    void testApplySessionDoesNotOverlapSleeplessNight() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 23, 22, 0),
                        LocalDateTime.of(2026, 1, 23, 23, 30),
                        Quality.GOOD)
        );
        Object result = function.apply(sessions);
        assertEquals(0L, result);
    }

    @Test
    void testApplySingleSessionOverlapsNightNoSleeplessNight() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 23, 23, 0),
                        LocalDateTime.of(2026, 1, 24, 1, 0),
                        Quality.GOOD)
        );
        Object result = function.apply(sessions);
        assertEquals(0L, result);
    }

    @Test
    void testApplySessionsCrossMonthBoundarySleeplessNight() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 30, 23, 0),
                        LocalDateTime.of(2026, 1, 31, 8, 30),
                        Quality.GOOD),
                new SleepingSession(
                        LocalDateTime.of(2026, 2, 1, 22, 0),
                        LocalDateTime.of(2026, 2, 2, 4, 30),
                        Quality.NORMAL)
        );

        Object result = function.apply(sessions);
        assertEquals(1L, result);
    }

    @Test
    void testApplyCrossMonthBoundaryWithSleeplessNight() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 30, 23, 0),
                        LocalDateTime.of(2026, 1, 31, 5, 30),
                        Quality.GOOD),
                new SleepingSession(
                        LocalDateTime.of(2026, 2, 2, 22, 0),
                        LocalDateTime.of(2026, 2, 3, 4, 0),
                        Quality.NORMAL)
        );

        Object result = function.apply(sessions);
        assertEquals(2L, result);
    }

    @Test
    void testApplyFirstSessionStartsAfterNoon() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 2, 1, 14, 0),
                        LocalDateTime.of(2026, 2, 1, 22, 0),
                        Quality.BAD),
                new SleepingSession(
                        LocalDateTime.of(2026, 2, 3, 23, 0),
                        LocalDateTime.of(2026, 2, 4, 7, 0),
                        Quality.GOOD)
        );

        Object result = function.apply(sessions);
        assertEquals(2L, result);
    }
}