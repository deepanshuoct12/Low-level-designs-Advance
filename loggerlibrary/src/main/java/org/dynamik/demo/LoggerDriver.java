package org.dynamik.demo;

import org.dynamik.constants.LogLevel;
import org.dynamik.constants.LoggerType;
import org.dynamik.factory.LoggerFactory;
import org.dynamik.model.AsyncLogger;
import org.dynamik.model.Logger;
import org.dynamik.model.LoggerConfig;
import org.dynamik.model.Sink;
import org.dynamik.model.StdoutSink;

import java.util.ArrayList;
import java.util.List;

public class LoggerDriver {
    private LoggerFactory loggerFactory = new LoggerFactory();
    
    public void demo() {
        LoggerConfig loggerConfig = initLoggerConfig(LoggerType.SYNC);
        Logger logger = loggerFactory.getLogger(loggerConfig);
        logger.info("Info message");
        logger.warn("warn message");
        logger.fatal("fatal message");
        logger.error("error message");

        System.out.println("\n--- Testing Async Logger ---");
        LoggerConfig asyncLoggerConfig = initLoggerConfig(LoggerType.ASYNC);
        AsyncLogger asyncLogger = (AsyncLogger) loggerFactory.getLogger(asyncLoggerConfig);
        
        // Test normal async logging
        for (int i = 0; i < 10; i++) {
            asyncLogger.info("Info message async " + i);
        }
        
        // Test shutdown behavior
        System.out.println("Shutting down async logger...");
        asyncLogger.shutdown();
        
        // Try to log after shutdown
        asyncLogger.info("This message should not be logged");
    }

    private LoggerConfig initLoggerConfig(LoggerType loggerType) {
        List<Sink> sinks = new ArrayList<>();
        sinks.add(new StdoutSink(LogLevel.INFO));
        return new LoggerConfig("logger-1", 25, loggerType, sinks);
    }
}
