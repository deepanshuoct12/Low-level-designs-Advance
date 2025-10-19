package org.company.model;

import lombok.Data;

@Data
public class Option {
    private String id;
    private String questionId;
    private Integer option;  // 1,2,3,4 // 1,2 for true, false
    private String optionText;
}
