package org.dynamik.model;

import org.dynamik.constants.LogLevel;

public abstract class Sink {
    private LogLevel logLevel;

    public Sink(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public void log(Message message) {
        if (message.getLogLevel().getPriority() >= logLevel.getPriority()) {
            write(message);
        }
    }

    protected abstract void write(Message message);
}
