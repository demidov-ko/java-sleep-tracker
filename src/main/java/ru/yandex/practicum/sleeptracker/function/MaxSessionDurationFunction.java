package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.sleepsession.SleepingSession;

import java.util.List;

public class MaxSessionDurationFunction implements AnalysisFunction {

    @Override
    public String getName() {
        return "Максимальная продолжительность сессии (мин)";
    }

    @Override
    public Object apply(List<SleepingSession> session) {
        if (session.isEmpty()) {
            return 0L;
        }
        return session.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .max()
                .orElse(0L);
    }
}
