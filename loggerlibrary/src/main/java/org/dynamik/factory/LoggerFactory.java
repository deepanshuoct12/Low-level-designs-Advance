package org.dynamik.factory;

import org.dynamik.constants.LoggerType;
import org.dynamik.model.AsyncLogger;
import org.dynamik.model.Logger;
import org.dynamik.model.LoggerConfig;
import org.dynamik.model.SyncLogger;

public class LoggerFactory {
    public Logger getLogger(LoggerConfig loggerConfig) {
        LoggerType loggerType = loggerConfig.getLoggerType();

        switch (loggerType) {
            case SYNC -> {
                return new SyncLogger(loggerConfig);
            }

            case ASYNC -> {
                return new AsyncLogger(loggerConfig);
            }
        }

        return null;
    }
}
