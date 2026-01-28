package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.sleepsession.SleepingSession;

import java.util.List;

public class AverageSessionDurationFunction implements AnalysisFunction {

    @Override
    public String getName() {
        return "Средняя продолжительность сессии (мин)";
    }

    @Override
    public Object apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return 0.0;
        }
        double average = sessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .average()
                .orElse(0.0);

        return String.format("%.2f", average);
    }
}
