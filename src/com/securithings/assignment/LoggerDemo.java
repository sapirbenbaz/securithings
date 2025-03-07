package com.securithings.assignment;

public class LoggerDemo {
    public static void main(String[] args) {
        LogManager logManager = LogManager.getInstance();
        logManager.addAppender(new ConsoleAppender());
        logManager.addAppender(new FileAppender("logs.txt"));

        logManager.setLogLevel(LoggerDemo.class.getName(), LogLevel.DEBUG);

        Logger logger = new Logger(LoggerDemo.class);

        logger.info("Application started");
        logger.debug("Debugging message");
        logger.error("An error occurred");
    }
}
