package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.sleepsession.Quality;
import ru.yandex.practicum.sleeptracker.sleepsession.SleepingSession;

import java.util.List;

public class BadQualitySessionsCountFunction implements AnalysisFunction {

    @Override
    public String getName() {
        return "Количество сессий с плохим качество сна";
    }

    @Override
    public Object apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return 0L;
        }

        long count = sessions.stream()
                .filter(session -> session.getQuality() == Quality.BAD)
                .count();
        return count;
    }
}
