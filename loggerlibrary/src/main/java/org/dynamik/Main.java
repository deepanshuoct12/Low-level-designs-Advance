package org.dynamik;

import org.dynamik.demo.LoggerDriver;

public class Main {
    private static LoggerDriver loggerDriver = new LoggerDriver();

    public static void main(String[] args) {
        System.out.println("Logger system");
        loggerDriver.demo();
    }
}