package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.sleepsession.SleepingSession;

import java.util.List;

public class CountSessionsFunction implements AnalysisFunction {
    @Override
    public Object apply(List<SleepingSession> sessions) {
        return sessions.size();
    }

    @Override
    public String getName() {
        return "Количество сессий сна";
    }
}
