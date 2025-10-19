package org.company.service;

import org.company.dao.QuestionDao;
import org.company.dao.QuizDao;
import org.company.enums.QuestionType;
import org.company.model.Answer;
import org.company.model.Option;
import org.company.model.Question;
import org.company.model.Quiz;
import org.company.model.QuizAttempt;
import org.company.stratergy.IQuestionStratergy;
import org.company.stratergy.McqStratergy;
import org.company.stratergy.TrueFalseStratergy;

import java.util.*;
import java.util.UUID;

public class QuizServiceImpl implements IQuizService {
    private  QuestionDao questionDao;
    private  QuizDao quizDao;
    private  AnswerService answerService;
    private  OptionService optionService;
    public static QuizServiceImpl quizService;
    private QuizService quizzService;
    private Map<QuestionType, IQuestionStratergy> stratergies = new HashMap<>();
    private QuizAttemptService quizAttemptService = new QuizAttemptService();

    public QuizServiceImpl() {
        this.questionDao = new QuestionDao();
        this.quizDao = new QuizDao();
        this.answerService = new AnswerService();
        this.optionService = new OptionService();
        this.quizzService = new QuizService();
        stratergies.put(QuestionType.MULTIPLE_CHOICE, new McqStratergy());
        stratergies.put(QuestionType.TRUE_FALSE, new TrueFalseStratergy());
        stratergies.put(QuestionType.MCQ, new McqStratergy());
    }

    public static QuizServiceImpl getInstance() {
        if (quizService == null) {
            synchronized (QuizServiceImpl.class) {
                if (quizService == null) {
                    quizService = new QuizServiceImpl();
                }
            }
        }

        return quizService;
    }



    @Override
    public void addQuestionToQuiz(String questionId, String quizId, QuestionType questionType) {
        Question question = questionDao.get(questionId);
        Quiz quiz = quizDao.get(quizId);
        
        if (question != null && quiz != null) {
            question.setQuizId(quizId);
            question.setQuestionType(questionType);
            questionDao.update(question);
        }
    }

    @Override
    public void removeQuestionFromQuiz(String questionId, String quizId) {
        Question question = questionDao.get(questionId);
        if (question != null && quizId.equals(question.getQuizId())) {
            question.setQuizId(null);
            questionDao.update(question);
        }
    }

    @Override
    public void addAnswerToQuestion(String questionId, String answerId) {
        // Implementation depends on how answers are associated with questions
        // This is a placeholder implementation

        // this is optopm id
        Answer answer = answerService.getAnswer(answerId);
        if (answer == null) {
            answer = new Answer();
            answer.setOptionId(answerId);
            answer.setQuestionId(questionId);
            answer.setId(UUID.randomUUID().toString());
            answerService.updateAnswer(answer);
        } else if (answer != null) {
            answer.setQuestionId(questionId);
            answerService.updateAnswer(answer);
        }
    }

    @Override
    public void removeAnswerFromQuestion(String questionId, String answerId) {
        Answer answer = answerService.getAnswer(answerId);
        if (answer != null && questionId.equals(answer.getQuestionId())) {
            answer.setQuestionId(null);
            answerService.updateAnswer(answer);
        }
    }

    @Override
    public void addOptionToQuestion(String questionId, String optionId) {
        // Implementation depends on how options are associated with questions
        // This is a placeholder implementation
        Option option = optionService.getOption(optionId);
        if (option != null) {
            option.setQuestionId(questionId);
            optionService.updateOption(option);
            
            // Also add to question's options list
            Question question = questionDao.get(questionId);
            if (question != null) {
                List<String> options = Optional.ofNullable(question.getOptions())
                                             .orElse(new ArrayList<>());
                if (!options.contains(optionId)) {
                    options.add(optionId);
                    question.setOptions(options);
                    questionDao.update(question);
                }
            }
        }
    }

    @Override
    public void removeOptionFromQuestion(String questionId, String optionId) {
        org.company.model.Option option = optionService.getOption(optionId);
        if (option != null && questionId.equals(option.getQuestionId())) {
            // Remove from question's options list
            Question question = questionDao.get(questionId);
            if (question != null && question.getOptions() != null) {
                question.getOptions().remove(optionId);
                questionDao.update(question);
            }
            
            // Remove the option-question association
            option.setQuestionId(null);
            optionService.updateOption(option);
        }
    }

    @Override
    public List<Question> displayQuestion(String quizId) {
        // This is a simplified implementation
        // In a real application, you might want to add pagination, filtering, etc.
        List<Question> allQuestions = new ArrayList<>();
        // This would be more efficient with a proper query in the DAO
        // that filters by quizId
        questionDao.getQuestionsByQuizId(quizId).forEach(allQuestions::add);
        
        return allQuestions;
    }

    // The following methods from IQuizService will be implemented later
    @Override
    public void answerQuestion(String currentUserId, String questionId, String optionId) {
        // Get the current question
        Question question = questionDao.get(questionId);
        if (question == null) {
            throw new IllegalArgumentException("Question not found with id: " + questionId);
        }
        
        // Get the current user's active quiz attempt
        QuizAttempt attempt = quizAttemptService.getByUserIdAndQuizId(currentUserId, question.getQuizId());
        if (attempt == null) {
            attempt = getOrCreateActiveAttempt(question.getQuizId(), currentUserId);
            quizAttemptService.updateQuizAttempt(attempt);
        }

        // Get the appropriate strategy for the question type
        IQuestionStratergy strategy = stratergies.get(question.getQuestionType());
        if (strategy == null) {
            throw new IllegalStateException("No strategy found for question type: " + question.getQuestionType());
        }
        
        // Calculate score for this answer
        int pointsEarned = strategy.computeScore(questionId, optionId);
        
        // Update the attempt
        if (attempt.getAnswers() == null) {
            attempt.setAnswers(new HashMap<>());
        }
        
        // Check if the question was already answered
        boolean wasAlreadyAnswered = attempt.getAnswers().containsKey(questionId);
        
        // Store the answer
        attempt.getAnswers().put(questionId, optionId);
        
        // Update the score
        if (wasAlreadyAnswered) {
            // In a real implementation, you might want to adjust the score
            // by removing previously earned points for this question
        } else {
            attempt.setScore(attempt.getScore() + pointsEarned);
        }
        
        // In a real application, you would save the updated attempt to the database
        // For example: quizAttemptDao.save(attempt);
    }
    

    
    /**
     * Gets an active quiz attempt for the specified user and quiz,
     * or creates a new one if none exists.
     * 
     * @param quizId the ID of the quiz
     * @param userId the ID of the user
     * @return the active quiz attempt
     */
    private QuizAttempt getOrCreateActiveAttempt(String quizId, String userId) {
        QuizAttempt attempt = new QuizAttempt();
        attempt.setId(UUID.randomUUID().toString());
        attempt.setQuizId(quizId);
        attempt.setUserId(userId);
        attempt.setScore(0);
        attempt.setTotalQuestions(0); // This would be set based on the quiz
        attempt.setAnswers(new HashMap<>());
        
        return attempt;
    }

    @Override
    public void saveQuiz(Quiz quiz) {
        quizzService.save(quiz);
    }

    public OptionService getOptionService() {
        return optionService;
    }

    public QuestionDao getQuestionDao() {
        return questionDao;
    }
}
