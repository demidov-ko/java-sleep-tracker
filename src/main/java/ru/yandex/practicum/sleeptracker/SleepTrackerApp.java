package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.function.*;
import ru.yandex.practicum.sleeptracker.sleepsession.SessionParser;
import ru.yandex.practicum.sleeptracker.sleepsession.SleepingSession;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SleepTrackerApp {
    //src/main/resources/sleep_log.txt   путь к файлу

    // Список всех аналитических функций
    private final List<AnalysisFunction> functions = new ArrayList<>();
    // Хранилище сессий сна
    private List<SleepingSession> sessions = new ArrayList<>();

    public SleepTrackerApp() {
        functions.add(new CountSessionsFunction());
        functions.add(new MinSessionDurationFunction());
        functions.add(new MaxSessionDurationFunction());
        functions.add(new AverageSessionDurationFunction());
        functions.add(new BadQualitySessionsCountFunction());
        functions.add(new SleeplessNightsCountFunction());
        functions.add(new TheClassifyUserFunction());
    }

    // Чтение файла и заполнение списка сессий
    private void loadSessions(String filePath) throws IOException {
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            throw new IOException("Файл не найден: " + filePath);
        }
        sessions = Files.readAllLines(path)
                .stream()
                .filter(line -> !line.trim().isEmpty())
                .map(SessionParser::parse)
                .collect(Collectors.toList());
    }

    public List<SleepingSession> getSessions() {
        return List.copyOf(sessions);
    }

    public void setSessions(List<SleepingSession> sessions) {
        this.sessions = sessions;
    }

    public List<AnalysisFunction> getFunctions() {
        return List.copyOf(functions);
    }

    // Запуск всех функций анализа
    public void runAnalysis() {
        System.out.println("Результаты анализа:\n");

        functions.forEach(function -> {
            Object result = function.apply(sessions);
            System.out.printf("%s: %s%n", function.getName(), result);
        });
    }

    //----------------------------------------------------------------------------
    static void main(String[] args) {
        String filePath = null;

        if (args.length > 0) {
            filePath = args[0];
        } else {
            System.out.println("Пожалуйста, укажите путь к файлу с логом сна:");
            try (Scanner scanner = new Scanner(System.in)) {
                filePath = scanner.nextLine();
            }
        }
        SleepTrackerApp app = new SleepTrackerApp();

        try {
            app.loadSessions(filePath);
            app.runAnalysis();
        } catch (IOException e) {
            System.err.println("Ошибка при работе с файлом: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ошибка обработки данных: " + e.getMessage());
        }
    }
}
