package org.dynamik.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.dynamik.constants.LogLevel;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class Message {
    private String content;
    private LogLevel logLevel;
    private String timeStamp;
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");

    public Message(String content, LogLevel logLevel) {
        this.content = content;
        this.logLevel = logLevel;
        timeStamp =  LocalDateTime.now().format(dateTimeFormatter);
    }
}
