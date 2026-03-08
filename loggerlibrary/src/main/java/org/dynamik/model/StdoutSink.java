package org.dynamik.model;

import org.dynamik.constants.LogLevel;

public class StdoutSink extends Sink {

    public StdoutSink(LogLevel logLevel) {
        super(logLevel);
    }

    @Override
    public synchronized void write(Message message) {
        System.out.println(message.getTimeStamp() + " [" + message.getLogLevel() + "] " + message.getContent());
    }
}
