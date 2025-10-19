package org.company.service;

import org.company.enums.QuestionType;
import org.company.model.Question;
import org.company.model.Quiz;

import java.util.List;

public interface IQuizService {
    void addQuestionToQuiz(String questionId, String quizId, QuestionType questionType);
    void removeQuestionFromQuiz(String questionId, String quizId);
    void addAnswerToQuestion(String questionId, String answerId);
    void removeAnswerFromQuestion(String questionId, String answerId);
    void addOptionToQuestion(String questionId, String optionId);
    void removeOptionFromQuestion(String questionId, String optionId);
    List<Question> displayQuestion(String quizId);  // display question tupe as well
    void answerQuestion(String currentUserId, String questionId, String optionId);
    void saveQuiz(Quiz quiz);
}
