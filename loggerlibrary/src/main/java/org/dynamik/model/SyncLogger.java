package org.dynamik.model;

import org.dynamik.constants.LogLevel;

import java.util.List;

public class SyncLogger extends Logger {

    public SyncLogger(LoggerConfig loggerConfig) {
        super(loggerConfig);
    }

    @Override
    protected void log(String msg, LogLevel logLevel) {
        Message message = new Message(msg, logLevel);
       for (Sink sink : sinks) {
           sink.log(message);
       }
    }
}
