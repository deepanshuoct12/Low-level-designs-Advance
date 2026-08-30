package org.project.model;

import lombok.Data;

@Data
public class Answer extends BaseEntity {
    private String questionId;
    private String authorId;
    private String body;
    private boolean accepted;
}
