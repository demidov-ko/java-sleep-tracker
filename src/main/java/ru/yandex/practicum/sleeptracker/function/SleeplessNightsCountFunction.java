package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.sleepsession.NightUtils;
import ru.yandex.practicum.sleeptracker.sleepsession.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Stream;

public class SleeplessNightsCountFunction implements AnalysisFunction {
    @Override
    public String getName() {
        return "Количество бессонных ночей";
    }

    @Override
    public Object apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) return 0L;

        LocalDateTime logStart = sessions.stream()
                .map(SleepingSession::getStartTime)
                .min(LocalDateTime::compareTo)
                .orElseThrow();

        LocalDateTime logEnd = sessions.stream()
                .map(SleepingSession::getEndTime)
                .max(LocalDateTime::compareTo)
                .orElseThrow();

        LocalDate startNight = logStart.toLocalTime().isBefore(LocalTime.NOON)
                ? logStart.toLocalDate().minusDays(1)
                : logStart.toLocalDate();

        LocalDate endNight = logEnd.toLocalDate();

        long nights = ChronoUnit.DAYS.between(startNight, endNight) + 1;

        return Stream.iterate(startNight, day -> day.plusDays(1))
                .limit(nights)
                .filter(night -> NightUtils.nightIntersectsLogging(night, logStart, logEnd))
                .filter(night -> NightUtils.isNightUncovered(night, sessions))
                .count();
    }
}
