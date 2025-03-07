package com.securithings.assignment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private final String className;
    private final LogManager logManager = LogManager.getInstance();

    public Logger(Class<?> classObject) {
        this.className = classObject.getName();
    }

    private void log(LogLevel level, String message) {
        if (level.ordinal() <= logManager.getLogLevel(className).ordinal()) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
            String threadName = Thread.currentThread().getName();
            String logMessage = String.format("%s ([%s]) [%s] [%s] - %s", timestamp, threadName, level, className, message);
            logManager.log(level, logMessage);
        }
    }

    // Additional log levels of the 7 can be added underneath

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }
}