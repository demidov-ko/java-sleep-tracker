package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.function.AnalysisFunction;
import ru.yandex.practicum.sleeptracker.sleepsession.SleepingSession;

import java.time.LocalDateTime;

import ru.yandex.practicum.sleeptracker.sleepsession.Quality;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SleepTrackerAppTest {
    private SleepTrackerApp app;

    @BeforeEach
    void setUp() {
        app = new SleepTrackerApp();
    }

    @Test
    void testRunAnalysisWithSessions() {
        SleepingSession session1 = new SleepingSession(
                LocalDateTime.of(2026, 1, 23, 22, 0),
                LocalDateTime.of(2026, 1, 24, 6, 30),
                Quality.GOOD
        );
        SleepingSession session2 = new SleepingSession(
                LocalDateTime.of(2026, 1, 24, 23, 15),
                LocalDateTime.of(2026, 1, 25, 7, 45),
                Quality.BAD
        );

        app.setSessions(List.of(session1, session2));
        app.runAnalysis();

        List<SleepingSession> loadedSessions = app.getSessions();
        assertEquals(2, loadedSessions.size());
        assertEquals(session1.getStartTime(), loadedSessions.get(0).getStartTime());
        assertEquals(session2.getEndTime(), loadedSessions.get(1).getEndTime());
    }

    @Test
    void testFunctionsListContainsAllExpected() {
        List<String> functionNames = app.getFunctions().stream()
                .map(AnalysisFunction::getName)
                .toList();

        assertTrue(functionNames.contains("Количество сессий сна"));
        assertTrue(functionNames.contains("Минимальная продолжительность сессии (мин)"));
        assertTrue(functionNames.contains("Максимальная продолжительность сессии (мин)"));
        assertTrue(functionNames.contains("Средняя продолжительность сессии (мин)"));
        assertTrue(functionNames.contains("Количество сессий с плохим качество сна"));
        assertTrue(functionNames.contains("Количество бессонных ночей"));
        assertTrue(functionNames.contains("Хронотип пользователя"));
    }

}