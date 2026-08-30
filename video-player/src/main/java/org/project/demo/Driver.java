package org.project.demo;

import org.project.enums.ContentType;
import org.project.enums.Genre;
import org.project.enums.WatchState;
import org.project.model.Content;
import org.project.model.Feed;
import org.project.model.User;
import org.project.model.Watch;
import org.project.service.ContentService;
import org.project.service.FeedService;
import org.project.service.UserService;
import org.project.service.VideoPlayerService;

import java.util.List;

public class Driver {

    public void runDemo() {
        UserService userService = new UserService();
        ContentService contentService = new ContentService();
        FeedService feedService = new FeedService();
        VideoPlayerService videoPlayerService = VideoPlayerService.getInstance();

        // Create user
        User user = new User();
        user.setUsername("john_doe");
        user.setEmail("john@example.com");
        user.setPreferences(Genre.ACTION);
        User createdUser = userService.create(user);
        System.out.println("Created User: " + createdUser.getUsername() + " with ID: " + createdUser.getId());

        // Create content
        Content content1 = new Content();
        content1.setTitle("Action Movie");
        content1.setDescription("An exciting action movie");
        content1.setType(ContentType.VIDEO);
        content1.setDuration(7200L);
        content1.setGenre(Genre.ACTION);
        content1.setLanguage("en");
        content1.setRating(4.5f);
        Content createdContent1 = contentService.create(content1);
        System.out.println("Created Content: " + createdContent1.getTitle() + " with ID: " + createdContent1.getId());

        Content content2 = new Content();
        content2.setTitle("Comedy Show");
        content2.setDescription("A funny comedy show");
        content2.setType(ContentType.VIDEO);
        content2.setDuration(5400L);
        content2.setGenre(Genre.COMEDY);
        content2.setLanguage("en");
        content2.setRating(4.0f);
        Content createdContent2 = contentService.create(content2);
        System.out.println("Created Content: " + createdContent2.getTitle() + " with ID: " + createdContent2.getId());

        // Add content to user's feed
        Feed feed1 = new Feed();
        feed1.setUserId(createdUser.getId());
        feed1.setContentId(createdContent1.getId());
        feedService.create(feed1);

        Feed feed2 = new Feed();
        feed2.setUserId(createdUser.getId());
        feed2.setContentId(createdContent2.getId());
        feedService.create(feed2);
        System.out.println("Added content to user's feed");

        // Fetch feed
        System.out.println("\n--- Fetching Feed ---");
        List<Content> feed = videoPlayerService.getFeed(createdUser.getId());
        System.out.println("User's feed contains " + feed.size() + " items:");
        feed.forEach(c -> System.out.println(" - " + c.getTitle() + " (" + c.getGenre() + ")"));

        // Watch content
        System.out.println("\n--- Watching Content ---");
        Watch watch = videoPlayerService.watchContent(createdUser.getId(), createdContent1.getId());
        System.out.println("Started watching: " + createdContent1.getTitle());
        System.out.println("Watch state: " + watch.getState());
        System.out.println("Watched duration: " + watch.getWatchedDuration() + "s");

        // Update progress
        System.out.println("\n--- Updating Progress ---");
        Watch updatedWatch = videoPlayerService.updateProgress(createdUser.getId(), createdContent1.getId(), 1800L, 1800L);
        System.out.println("Updated progress for: " + createdContent1.getTitle());
        System.out.println("Watch state: " + updatedWatch.getState());
        System.out.println("Watched duration: " + updatedWatch.getWatchedDuration() + "s");
        System.out.println("Last position: " + updatedWatch.getLastWatchedPosition() + "s");

        // Resume content
        System.out.println("\n--- Resuming Content ---");
        Watch resumedWatch = videoPlayerService.resumeContent(createdUser.getId(), createdContent1.getId());
        System.out.println("Resumed: " + createdContent1.getTitle());
        System.out.println("Current progress: " + resumedWatch.getWatchedDuration() + "s");
        System.out.println("Watch state: " + resumedWatch.getState());

        // Filter content by genre
        System.out.println("\n--- Filtering Content by Genre ---");
        List<Content> actionContent = videoPlayerService.filterContent(org.project.enums.FilterContentTerm.GENRE);
        System.out.println("Action content found: " + actionContent.size());
        actionContent.forEach(c -> System.out.println(" - " + c.getTitle()));

        System.out.println("\n--- Demo Completed Successfully ---");
    }
}
