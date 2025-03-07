package com.securithings.assignment;

public class ConsoleAppender implements LogAppender {
    public void append(String message) {
        System.out.println(message);
    }
}