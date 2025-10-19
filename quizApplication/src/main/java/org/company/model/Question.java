package org.company.model;

import lombok.Data;
import org.company.enums.QuestionType;

import java.util.List;

@Data
public class Question {
    private String question;
    private String id;
    private QuestionType questionType;
    private String quizId;
    private List<String> options; // for MCQ questions 4 options will be there and for TRUE_FALSE 2 options will be there
}
