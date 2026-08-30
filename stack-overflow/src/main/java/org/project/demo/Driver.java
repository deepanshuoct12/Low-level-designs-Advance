package org.project.demo;

import org.project.enums.FlagReason;
import org.project.enums.FlagStatus;
import org.project.enums.PostType;
import org.project.enums.Tag;
import org.project.enums.TargetType;
import org.project.enums.VoteType;
import org.project.model.Answer;
import org.project.model.Comment;
import org.project.model.Flag;
import org.project.model.Question;
import org.project.model.User;
import org.project.service.StackOverFlowService;
import org.project.service.UserService;

import java.util.List;

public class Driver {

    private final UserService userService = new UserService();
    private final StackOverFlowService stackOverFlowService = StackOverFlowService.getInstance();

    public void runDemo() {
        User alice = new User();
        alice.setUsername("alice");
        alice.setEmail("alice@example.com");
        alice = userService.create(alice);

        User bob = new User();
        bob.setUsername("bob");
        bob.setEmail("bob@example.com");
        bob = userService.create(bob);

        User charlie = new User();
        charlie.setUsername("charlie");
        charlie.setEmail("charlie@example.com");
        charlie = userService.create(charlie);

        Question question1 = stackOverFlowService.postQuestion(alice.getId(), "How does ConcurrentHashMap work?",
                "Explain the internal working of ConcurrentHashMap in Java.", List.of(Tag.JAVA, Tag.MULTITHREADING));
        Question question2 = stackOverFlowService.postQuestion(alice.getId(), "Best way to design a URL shortener?",
                "What is a good system design approach for a URL shortener?", List.of(Tag.SYSTEM_DESIGN));

        System.out.println("Alice unread notifications before any answer");

        Answer answer1 = stackOverFlowService.postAnswer(bob.getId(), question1.getId(),
                "ConcurrentHashMap uses bucket-level locking instead of locking the entire map.");
        Answer answer2 = stackOverFlowService.postAnswer(bob.getId(), question2.getId(),
                "Use base62 encoding with a distributed counter to generate short codes.");

        System.out.println("Alice unread notifications after answers (observer triggered):");

        Comment comment1 = stackOverFlowService.addComment(charlie.getId(), TargetType.QUESTION, question1.getId(),
                "Can you also cover resizing behavior?");
        Comment comment2 = stackOverFlowService.addComment(charlie.getId(), TargetType.ANSWER, answer1.getId(),
                "Nice explanation, thanks!");

        stackOverFlowService.addVote(bob.getId(), PostType.QUESTION, question1.getId(), VoteType.UPVOTE);
        stackOverFlowService.addVote(charlie.getId(), PostType.ANSWER, answer1.getId(), VoteType.UPVOTE);

        Flag flag = stackOverFlowService.report(charlie.getId(), TargetType.ANSWER, answer2.getId(), FlagReason.OFF_TOPIC);
        System.out.println("Answer2 flagged with status: " + flag.getStatus());

        boolean accepted = stackOverFlowService.acceptAnswer(alice.getId(), question1.getId(), answer1.getId());
        System.out.println("Answer1 accepted: " + accepted);

        Flag reviewedFlag = stackOverFlowService.reviewFlag(alice.getId(), flag.getId(), FlagStatus.ACTION_TAKEN);
        System.out.println("Flag reviewed, final status: " + reviewedFlag.getStatus());
    }
}
