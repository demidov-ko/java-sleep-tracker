package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.sleepsession.SleepingSession;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SleeplessNightsCountFunction implements AnalysisFunction {
    @Override
    public String getName() {
        return "Количество бессонных ночей";
    }

    @Override
    public Object apply(List<SleepingSession> sessions) {

        if (sessions.isEmpty()) return 0L;

        LocalDate minDate = sessions.stream()
                .map(session -> session.getStartTime().toLocalDate())
                .min(LocalDate::compareTo)
                .orElse(null);

        LocalDate maxDate = sessions.stream()
                .map(session -> session.getEndTime().toLocalDate())
                .max(LocalDate::compareTo)
                .orElse(null);

        if (minDate == null || maxDate == null) return 0L;

        long daysBetween = Period.between(minDate.plusDays(1), maxDate).getDays();


        Set<LocalDate> allDates = Stream.iterate(minDate.plusDays(1),
                        date -> date.plusDays(1))
                .limit(daysBetween + 1)
                .collect(Collectors.toSet());

        long sleeplessCount = allDates.stream()
                .filter(date -> sessions.stream().noneMatch(session -> {
                    LocalDate startDate = session.getStartTime().toLocalDate();
                    LocalDate endDate = session.getEndTime().toLocalDate();

                    if (!startDate.equals(date) && !endDate.equals(date)) {
                        return false;
                    }

                    LocalTime startTime = startDate.equals(date)
                            ? session.getStartTime().toLocalTime()
                            : LocalTime.MIDNIGHT;

                    LocalTime endTime = endDate.equals(date)
                            ? session.getEndTime().toLocalTime()
                            : LocalTime.MAX;

                    return startTime.isBefore(LocalTime.of(6, 0))
                            && endTime.isAfter(LocalTime.MIDNIGHT);
                }))
                .count();

        return sleeplessCount;
    }
}
