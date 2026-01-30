package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.sleepsession.SleepingSession;

import java.util.List;
import java.util.Locale;

public class AverageSessionDurationFunction implements AnalysisFunction {

    @Override
    public String getName() {
        return "Средняя продолжительность сессии (мин)";
    }

    @Override
    public String apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return "0.0";
        }
        double average = sessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .average()
                .orElse(0.0);

        return String.format(Locale.US, "%.2f", average);
    }
}
