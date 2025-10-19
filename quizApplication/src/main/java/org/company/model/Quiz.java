package org.company.model;

import lombok.Data;

@Data
public class Quiz {
    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private String category;
    private int totalQuestions;
}
