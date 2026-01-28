package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.function.TheClassifyUserFunction;
import ru.yandex.practicum.sleeptracker.sleepsession.Chronotype;
import ru.yandex.practicum.sleeptracker.sleepsession.Quality;
import ru.yandex.practicum.sleeptracker.sleepsession.SleepingSession;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TheClassifyUserFunctionTest {
    private TheClassifyUserFunction function;

    @BeforeEach
    void setUp() {
        function = new TheClassifyUserFunction();
    }

    @Test
    void testEmptySessionsReturnsGolub() {
        List<SleepingSession> sessions = List.of();
        Object result = function.apply(sessions);
        assertEquals(Chronotype.GOLUB, result);
    }

    @Test
    void testSingleSessionSowa() {
        List<SleepingSession> sessions = List.of(new SleepingSession(
                LocalDateTime.of(2026, 1, 23, 23, 30),
                LocalDateTime.of(2026, 1, 24, 9, 15),
                Quality.GOOD)
        );
        Object result = function.apply(sessions);
        assertEquals(Chronotype.SOWA, result);
    }

    @Test
    void testSingleSessionZhavoronok() {
        List<SleepingSession> sessions = List.of(new SleepingSession
                (LocalDateTime.of(2026, 1, 23, 21, 0),
                        LocalDateTime.of(2026, 1, 23, 6, 30),
                        Quality.BAD)
        );
        Object result = function.apply(sessions);
        assertEquals(Chronotype.ZHAVORONOK, result);
    }

    @Test
    void testMultipleSessionsMajoritySowa() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2026, 1, 23, 23, 15),
                        LocalDateTime.of(2026, 1, 24, 9, 10), Quality.GOOD),
                new SleepingSession(LocalDateTime.of(2026, 1, 24, 23, 45),
                        LocalDateTime.of(2026, 1, 25, 9, 20), Quality.GOOD),
                new SleepingSession(LocalDateTime.of(2026, 1, 25, 21, 30),
                        LocalDateTime.of(2026, 1, 25, 6, 45), Quality.GOOD)
        );
        Object result = function.apply(sessions);
        assertEquals(Chronotype.SOWA, result);
    }

}