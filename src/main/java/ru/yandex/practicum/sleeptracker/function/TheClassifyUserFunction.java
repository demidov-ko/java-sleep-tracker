package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.sleepsession.Chronotype;
import ru.yandex.practicum.sleeptracker.sleepsession.SleepingSession;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TheClassifyUserFunction implements AnalysisFunction {

    @Override
    public String getName() {
        return "Хронотип пользователя";
    }

    @Override
    public Object apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return Chronotype.GOLUB;
        }

        Map<Chronotype, Long> counts = sessions.stream()
                .map(session -> {
                    LocalTime sleepTime = session.getStartTime().toLocalTime();
                    LocalTime wakeTime = session.getEndTime().toLocalTime();

                    if (sleepTime == null || wakeTime == null) {
                        return Chronotype.GOLUB;
                    }

                    if (sleepTime.isAfter(LocalTime.of(23, 0)) &&
                            wakeTime.isAfter(LocalTime.of(9, 0))) {
                        return Chronotype.SOWA;
                    } else if (sleepTime.isBefore(LocalTime.of(22, 0)) &&
                            wakeTime.isBefore(LocalTime.of(7, 0))) {
                        return Chronotype.ZHAVORONOK;
                    } else {
                        return Chronotype.GOLUB;
                    }
                })
                .collect(Collectors.groupingBy(
                        type -> type,
                        Collectors.counting()
                ));

        long sowaCount = counts.getOrDefault(Chronotype.SOWA, 0L);
        long zhavoronokCount = counts.getOrDefault(Chronotype.ZHAVORONOK, 0L);
        long golubCount = counts.getOrDefault(Chronotype.GOLUB, 0L);

        // Определяем итоговый хронотип
        if (sowaCount > zhavoronokCount && sowaCount > golubCount) {
            return Chronotype.SOWA;
        }
        if (zhavoronokCount > sowaCount && zhavoronokCount > golubCount) {
            return Chronotype.ZHAVORONOK;
        }

        return Chronotype.GOLUB;

    }
}
