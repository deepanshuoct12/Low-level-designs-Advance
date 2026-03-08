package org.dynamik.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.dynamik.constants.LoggerType;

import java.util.List;

@Data
@AllArgsConstructor
public class LoggerConfig {
    private String loggerName;
    private int bufferSize;
    private LoggerType loggerType;
    private List<Sink> sinks;
}
