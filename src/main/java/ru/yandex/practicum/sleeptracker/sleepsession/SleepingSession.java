package ru.yandex.practicum.sleeptracker.sleepsession;

import java.time.Duration;
import java.time.LocalDateTime;

public class SleepingSession {
    private LocalDateTime startTime; // Начало сна
    private LocalDateTime endTime; // Конец сна
    private Quality quality; // Качество сна

    public SleepingSession(LocalDateTime startTime, LocalDateTime endTime, Quality quality) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.quality = quality;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Quality getQuality() {
        return quality;
    }

    // Метод для расчёта продолжительности сна в минутах
    public long getDurationInMinutes() {
        return Duration.between(startTime, endTime).toMinutes();
    }

    @Override
    public String toString() {
        return String.format("SleepingSession[%s → %s, %s, %d мин]",
                startTime, endTime, quality, getDurationInMinutes());
    }
}
