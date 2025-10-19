package org.company;

import org.company.enums.QuestionType;
import org.company.model.*;
import org.company.service.OptionService;
import org.company.service.QuizAttemptService;
import org.company.service.QuizServiceImpl;
import org.company.service.UserService;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Initialize services
        QuizServiceImpl quizServiceImpl = QuizServiceImpl.getInstance();
        UserService userService = new UserService();
        OptionService optionService = new OptionService();
        QuizAttemptService quizAttemptService = new QuizAttemptService();

        
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
        
        // Display all questions in the quiz with their options
        System.out.println("\nQuiz Questions:");
        List<Question> questions = quizServiceImpl.displayQuestion(quiz.getId());
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.printf("%d. %s (Type: %s)\n", 
                i + 1, 
                q.getQuestion(), 
                q.getQuestionType().toString());
            
            // Display options if they exist
            if (q.getOptions() != null && !q.getOptions().isEmpty()) {
                System.out.println("   Options:");
                Map<Character, String> optionMap = new HashMap<>();
                
                // Display all options with their IDs
                for (int j = 0; j < q.getOptions().size(); j++) {
                    String optionId = q.getOptions().get(j);
                    Option option = quizServiceImpl.getOptionService().getOption(optionId);
                    String optionText = option != null ? option.getOptionText() : "[Option not found]";
                    char optionChar = (char)('a' + j);
                    
                    System.out.printf("   %c. [ID: %s] %s\n", optionChar, optionId, optionText);
                    optionMap.put(optionChar, optionId);
                }
                
                // Get user input for answer
                boolean validInput = false;
                Scanner scanner = new Scanner(System.in);
                
                while (!validInput) {
                    System.out.print("\n   Your answer (enter option id): ");
                    String input = scanner.nextLine().trim().toLowerCase();
                    
                    if (input.length() == 1) {
                        char selected = input.charAt(0);
                        if (optionMap.containsKey(selected)) {
                            String selectedOptionId = optionMap.get(selected);
                            // Call answerQuestion with the option ID
                            quizServiceImpl.answerQuestion(user.getId(), q.getId(), selectedOptionId);
                            Option selectedOption = quizServiceImpl.getOptionService().getOption(selectedOptionId);
                            System.out.printf("   ✓ Selected: [ID: %s] %s\n", 
                                selectedOptionId,
                                selectedOption != null ? selectedOption.getOptionText() : "[Option not found]");
                            validInput = true;
                        } else {
                            System.out.println("   Invalid option. Please enter a valid letter from the options above.");
                        }
                    } else {
                        System.out.println("   Please enter a single letter.");
                    }
                }
            } else {
                System.out.println("   No options available for this question.");
            }
            System.out.println(); // Add a blank line between questions
        }

        // Display user's score
        QuizAttempt quizAttempt = quizAttemptService.getByUserIdAndQuizId(user.getId(), quiz.getId());
        int score = quizAttempt.getScore();
        System.out.println("\nYour score: " + score + " out of " + quiz.getTotalQuestions());
        System.out.printf("\nYour score: %d out of %d\n", score, quiz.getTotalQuestions());
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