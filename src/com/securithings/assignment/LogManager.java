package com.securithings.assignment;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class LogManager {
    private static final LogManager instance = new LogManager();
    private final Map<String, LogLevel> logLevelConfig = new ConcurrentHashMap<>();
    private final LinkedBlockingQueue<String> logQueue = new LinkedBlockingQueue<>();
    private final Set<LogAppender> logAppenders = Collections.synchronizedSet(new HashSet<>());

    private LogManager() {
        new Thread(() -> {
            while (true) {
                try {
                    String logMessage = logQueue.take();
                    synchronized (logAppenders) {
                        for (LogAppender appender : logAppenders) {
                            appender.append(logMessage);
                        }
                    }
                } catch (InterruptedException ignored) {}
            }
        }).start();
    }

    public static LogManager getInstance() {
        return instance;
    }

    public void setLogLevel(String identifier, LogLevel level) {
        logLevelConfig.put(identifier, level);
    }

    public LogLevel getLogLevel(String identifier) {
        if (logLevelConfig.containsKey(identifier)) {
            return logLevelConfig.get(identifier);
        }

        String packageName = identifier;
        while (packageName.contains(".")) {
            packageName = packageName.substring(0, packageName.lastIndexOf('.'));
            if (logLevelConfig.containsKey(packageName)) {
                return logLevelConfig.get(packageName);
            }
        }

        return LogLevel.INFO;
    }

    public void addAppender(LogAppender appender) {
        logAppenders.add(appender);
    }

    public void log(LogLevel level, String message) {
        logQueue.add(message);
    }
}