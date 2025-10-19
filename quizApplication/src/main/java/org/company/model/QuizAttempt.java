package org.company.model;

import lombok.Data;

import java.util.HashMap;

@Data
public class QuizAttempt {
    private String id;
    private String quizId;
    private String userId;
    private int score;
    private int totalQuestions;
    private HashMap<String, String> answers; // questionId -> answer , answer contains option
}
