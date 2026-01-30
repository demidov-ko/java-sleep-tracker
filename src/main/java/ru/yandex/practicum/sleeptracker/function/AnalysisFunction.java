package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.sleepsession.SleepingSession;

import java.util.List;
import java.util.function.Function;

//интерфейс для аналитических функций
public interface AnalysisFunction extends Function<List<SleepingSession>, Object> {
    String getName();
}
