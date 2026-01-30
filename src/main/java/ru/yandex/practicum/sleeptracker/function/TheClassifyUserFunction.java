package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.sleepsession.Chronotype;
import ru.yandex.practicum.sleeptracker.sleepsession.NightUtils;
import ru.yandex.practicum.sleeptracker.sleepsession.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        Map<Chronotype, Long> counts = Stream.iterate(startNight, d -> d.plusDays(1))
                .limit(nights)
                .filter(night -> NightUtils.nightIntersectsLogging(night, logStart, logEnd))
                .filter(night -> !NightUtils.isNightUncovered(night, sessions))
                .map(night -> classifyNight(night, sessions))
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()
                ));

        long sowa = counts.getOrDefault(Chronotype.SOWA, 0L);
        long zhavoronok = counts.getOrDefault(Chronotype.ZHAVORONOK, 0L);
        long golub = counts.getOrDefault(Chronotype.GOLUB, 0L);

        if (sowa > zhavoronok && sowa > golub) return Chronotype.SOWA;
        if (zhavoronok > sowa && zhavoronok > golub) return Chronotype.ZHAVORONOK;
        return Chronotype.GOLUB;
    }

    private Chronotype classifyNight(LocalDate night, List<SleepingSession> sessions) {
        LocalDateTime nightStart = night.atTime(0, 0);
        LocalDateTime nightEnd = night.atTime(6, 0);

        SleepingSession session = sessions.stream()
                .filter(s -> s.getStartTime().isBefore(nightEnd) &&
                        s.getEndTime().isAfter(nightStart)
                )
                .findFirst()
                .orElseThrow();

        LocalTime sleepTime = session.getStartTime().toLocalTime();
        LocalTime wakeTime = session.getEndTime().toLocalTime();

        if (sleepTime.isAfter(LocalTime.of(23, 0)) &&
                wakeTime.isAfter(LocalTime.of(9, 0))) {
            return Chronotype.SOWA;
        }
        if (sleepTime.isBefore(LocalTime.of(22, 0)) &&
                wakeTime.isBefore(LocalTime.of(7, 0))) {
            return Chronotype.ZHAVORONOK;
        }
        return Chronotype.GOLUB;
    }
}
