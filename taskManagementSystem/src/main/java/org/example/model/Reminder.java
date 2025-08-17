package org.example.model;

import lombok.Data;

@Data
public class Reminder {
    private String id;
    private String message;
    private String taskId;
    private Long time; // epoch time
}
