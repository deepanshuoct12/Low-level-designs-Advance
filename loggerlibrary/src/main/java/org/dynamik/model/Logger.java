package org.dynamik.model;

import lombok.Data;
import org.dynamik.constants.LogLevel;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public abstract class Logger {
    private String loggerName;
    protected List<Sink> sinks;

    public Logger(LoggerConfig loggerConfig) {
        this.loggerName = loggerConfig.getLoggerName();
        this.sinks = loggerConfig.getSinks();
    }

    public void info(String msg) {
        log(msg, LogLevel.INFO);
    }

    public void error(String msg) {
        log(msg, LogLevel.ERROR);
    }

    public void fatal(String msg) {
        log(msg, LogLevel.FATAL);
    }

    public void warn(String msg) {
        log(msg, LogLevel.WARN);
    }

    protected abstract void log(String msg, LogLevel logLevel);
}
