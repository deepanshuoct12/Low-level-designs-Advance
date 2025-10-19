package org.company.model;

import lombok.Data;

@Data
public class Answer {
    private String id;
    private String questionId;
    private String optionId;
}
