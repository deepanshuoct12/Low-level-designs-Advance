package org.example.model;

import lombok.Data;
import org.example.enums.TaskStatus;

import java.util.Date;

@Data
public class Task {
    private String id;
    private String title;
    private String description;
    private TaskStatus status;
    private Date dueDate;
    private Integer priority;
    private String assignedTo; // user id
    private String createdBy;  // user id

    public Long getDueDateInEpoch() {
        return dueDate.toInstant().getEpochSecond();
    }
}
