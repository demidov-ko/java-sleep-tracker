package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.sleepsession.SleepingSession;

import java.util.List;

public class MinSessionDurationFunction implements AnalysisFunction{
    @Override
    public Object apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return 0L;
        }
        return sessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .min()
                .orElse(0L);
    }

    @Override
    public String getName() {
        return "Минимальная продолжительность сессии (мин)";
    }
}
