package org.company.demo;

import org.company.enums.QuestionType;
import org.company.model.Option;
import org.company.model.Question;
import org.company.model.Quiz;
import org.company.model.User;
import org.company.service.OptionService;
import org.company.service.QuizServiceImpl;
import org.company.service.UserService;

import java.util.List;
import java.util.UUID;

public class QuizDriver {

    public void demo() {
        QuizServiceImpl quizServiceImpl = QuizServiceImpl.getInstance();
        UserService userService = new UserService();
        OptionService optionService = new OptionService();


        // Create a user
        User user = userService.createUser("Quiz Player", "123-456-7890");
        System.out.println("Created user: " + user.getName() + " (ID: " + user.getId() + ")");

        // Create a quiz
        Quiz quiz = new Quiz();
        quiz.setId(UUID.randomUUID().toString());
        quiz.setName("General Knowledge Quiz");
        quiz.setDescription("Test your knowledge with this quiz!");
        quiz.setTotalQuestions(5);

        // Save the quiz
        quizServiceImpl.saveQuiz(quiz);
        System.out.println("\nCreated quiz: " + quiz.getName() + " (ID: " + quiz.getId() + ")");

        // Create and add MCQ questions
        addMCQQuestion(quizServiceImpl, quiz.getId(),
                "What is the capital of France?",
                List.of("London", "Berlin", "Paris", "Madrid"),
                2); // Paris is at index 2

        addMCQQuestion(quizServiceImpl, quiz.getId(),
                "Which planet is known as the Red Planet?",
                List.of("Venus", "Mars", "Jupiter", "Saturn"),
                1); // Mars is at index 1

        addMCQQuestion(quizServiceImpl, quiz.getId(),
                "What is 2 + 2?",
                List.of("3", "4", "5", "6"),
                1); // 4 is at index 1

        // Create and add True/False questions
        addTrueFalseQuestion(quizServiceImpl, quiz.getId(),
                "The Earth is flat.",
                false);

        addTrueFalseQuestion(quizServiceImpl, quiz.getId(),
                "The Great Wall of China is visible from space.",
                false);

        // Display all questions in the quiz
        System.out.println("\nQuiz Questions:");
        List<Question> questions = quizServiceImpl.displayQuestion(quiz.getId());
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.printf("%d. %s (Type: %s)\n",
                    i + 1,
                    q.getQuestion(),
                    q.getQuestionType().toString());
        }
    }

    private static void addMCQQuestion(QuizServiceImpl quizService, String quizId,
                                       String questionText, List<String> options, int correctOptionIndex) {
        // Create question
        Question question = new Question();
        question.setId(UUID.randomUUID().toString());
        question.setQuestion(questionText);
        question.setQuestionType(QuestionType.MCQ);
        question.setQuizId(quizId);

        // Save question
        quizService.getQuestionDao().save(question);

        // Add options
        for (int i = 0; i < options.size(); i++) {
            Option option = new Option();
            option.setId(UUID.randomUUID().toString());
            option.setQuestionId(question.getId());
            option.setOption(i + 1);
            option.setOptionText(options.get(i));

            // Save option
            quizService.getOptionService().updateOption(option);

            // Add option to question
            quizService.addOptionToQuestion(question.getId(), option.getId());

            // If this is the correct answer, create an answer
            if (i == correctOptionIndex) {
                quizService.addAnswerToQuestion(question.getId(), option.getId());
            }
        }

        // Add question to quiz
        quizService.addQuestionToQuiz(question.getId(), quizId, QuestionType.MCQ);
    }

    private static void addTrueFalseQuestion(QuizServiceImpl quizService, String quizId,
                                             String questionText, boolean correctAnswer) {
        // Create question
        Question question = new Question();
        question.setId(UUID.randomUUID().toString());
        question.setQuestion(questionText);
        question.setQuestionType(QuestionType.TRUE_FALSE);
        question.setQuizId(quizId);

        // Save question
        quizService.getQuestionDao().save(question);

        // Add True option
        Option trueOption = new Option();
        trueOption.setId(UUID.randomUUID().toString());
        trueOption.setQuestionId(question.getId());
        trueOption.setOption(1);
        trueOption.setOptionText("True");
        quizService.getOptionService().updateOption(trueOption);
        quizService.addOptionToQuestion(question.getId(), trueOption.getId());

        // Add False option
        Option falseOption = new Option();
        falseOption.setId(UUID.randomUUID().toString());
        falseOption.setQuestionId(question.getId());
        falseOption.setOption(2);
        falseOption.setOptionText("False");
        quizService.getOptionService().updateOption(falseOption);
        quizService.addOptionToQuestion(question.getId(), falseOption.getId());

        // Set correct answer
        if (correctAnswer) {
            quizService.addAnswerToQuestion(question.getId(), trueOption.getId());
        } else {
            quizService.addAnswerToQuestion(question.getId(), falseOption.getId());
        }

        // Add question to quiz
        quizService.addQuestionToQuiz(question.getId(), quizId, QuestionType.TRUE_FALSE);

    }
}
