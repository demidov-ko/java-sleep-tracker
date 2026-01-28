package ru.yandex.practicum.sleeptracker.sleepsession;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SessionParser {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.uu HH:mm");

    public static SleepingSession parse(String line) {

        String[] parts = line.trim().split(";");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Некорректный формат строки");
        }
        LocalDateTime startTime = LocalDateTime.parse(parts[0], FORMATTER);
        LocalDateTime endTime = LocalDateTime.parse(parts[1], FORMATTER);

        Quality quality = Quality.valueOf(parts[2].trim().toUpperCase());

        return new SleepingSession(startTime, endTime, quality);
    }
}
