package ru.yandex.practicum.sleeptracker.sleepsession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public final class NightUtils {
    public static boolean nightIntersectsLogging(LocalDate night, LocalDateTime logStart, LocalDateTime logEnd) {
        LocalDateTime nightStart = night.atTime(0, 0);
        LocalDateTime nightEnd = night.atTime(6, 0);

        return nightStart.isBefore(logEnd) && nightEnd.isAfter(logStart);
    }

    public static boolean isNightUncovered(LocalDate night, List<SleepingSession> sessions) {
        LocalDateTime nightStart = night.atTime(0, 0);
        LocalDateTime nightEnd = night.atTime(6, 0);

        return sessions.stream().noneMatch(session -> {
            LocalDateTime sessionStart = session.getStartTime();
            LocalDateTime sessionEnd = session.getEndTime();

            return sessionStart.isBefore(nightEnd) && sessionEnd.isAfter(nightStart);
        });
    }
}
