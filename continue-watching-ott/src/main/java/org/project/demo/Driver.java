package org.project.demo;

import org.project.enums.Genre;
import org.project.enums.SearchType;
import org.project.model.Content;
import org.project.enums.ContentType;
import org.project.model.User;
import org.project.model.WatchProgress;
import org.project.service.ContentService;
import org.project.service.OTTService;
import org.project.service.UserService;

import java.util.List;

public class Driver {
    private final UserService userService;
    private final ContentService contentService;
    private final OTTService ottService;

    public Driver() {
        this.userService = new UserService();
        this.contentService = new ContentService();
        this.ottService = OTTService.getInstance();
    }

    public void runDemo() {
        // Create a user
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPhoneNumber("1234567890");
        User createdUser = userService.create(user);
        System.out.println("Created User: " + createdUser.getName() + " (ID: " + createdUser.getId() + ")");

        // Create some content
        Content movie1 = new Content();
        movie1.setTitle("Title Action Movie");
        movie1.setDescription("An exciting action film");
        movie1.setDuration(7200L); // 2 hours in seconds
        movie1.setContentType(ContentType.MOVIE);
        movie1.setGenre(Genre.ACTION);
        Content createdMovie1 = contentService.create(movie1);

        Content movie2 = new Content();
        movie2.setTitle("Romantic Comedy");
        movie2.setDescription("A love story with humor");
        movie2.setDuration(5400L); // 1.5 hours in seconds
        movie2.setContentType(ContentType.MOVIE);
        movie2.setGenre(Genre.ROMANCE);
        Content createdMovie2 = contentService.create(movie2);

        Content movie3 = new Content();
        movie3.setTitle("Comedy Special");
        movie3.setDescription("Stand-up comedy show");
        movie3.setDuration(3600L); // 1 hour in seconds
        movie3.setContentType(ContentType.MOVIE);
        movie3.setGenre(Genre.COMEDY);
        Content createdMovie3 = contentService.create(movie3);

        System.out.println("\nCreated 3 movies:");
        System.out.println("- " + createdMovie1.getTitle() + " (" + createdMovie1.getGenre() + ")");
        System.out.println("- " + createdMovie2.getTitle() + " (" + createdMovie2.getGenre() + ")");
        System.out.println("- " + createdMovie3.getTitle() + " (" + createdMovie3.getGenre() + ")");

        // Search content by genre
        System.out.println("\n--- Searching for ACTION movies ---");
        List<Content> actionMovies = ottService.searchContent("ACTION", SearchType.GENRE);
        actionMovies.forEach(movie -> System.out.println("Found: " + movie.getTitle()));

        // Search content by title
        System.out.println("\n--- Searching for 'Comedy' in titles ---");
        List<Content> comedyMovies = ottService.searchContent("Comedy", SearchType.TITLE);
        comedyMovies.forEach(movie -> System.out.println("Found: " + movie.getTitle()));

        // User journey: Start watching a movie
        System.out.println("\n--- User Journey: Watching " + createdMovie1.getTitle() + " ---");
        WatchProgress watchProgress = ottService.startWatching(createdUser.getId(), createdMovie1.getId());
        System.out.println("Started watching at: " + watchProgress.getStartTimestamp());
        System.out.println("Status: " + watchProgress.getStatus());
        System.out.println("Position: " + watchProgress.getPositionSeconds() + " seconds");

        // Update progress (watching for 30 minutes)
        System.out.println("\n--- Watching for 30 minutes (1800 seconds) ---");
        WatchProgress updatedProgress = ottService.updateProgress(watchProgress.getId(), 1800L);
        System.out.println("Updated position: " + updatedProgress.getPositionSeconds() + " seconds");
        System.out.println("Status: " + updatedProgress.getStatus());

        // Stop watching
        System.out.println("\n--- Stopping the movie ---");
        WatchProgress stoppedProgress = ottService.stopWatching(watchProgress.getId());
        System.out.println("Stopped at: " + stoppedProgress.getStopTimestamp());
        System.out.println("Status: " + stoppedProgress.getStatus());
        System.out.println("Final position: " + stoppedProgress.getPositionSeconds() + " seconds");

        // Resume watching (start watching again)
        System.out.println("\n--- Resuming the movie ---");
        WatchProgress resumedProgress = ottService.resumeWatching(createdUser.getId(), createdMovie1.getId());
        System.out.println("Resumed at: " + resumedProgress.getLastTimestamp());
        System.out.println("Status: " + resumedProgress.getStatus());
        System.out.println("Position: " + resumedProgress.getPositionSeconds() + " seconds");

        // Update progress again (watching for another 45 minutes)
        System.out.println("\n--- Watching for another 45 minutes (2700 seconds) ---");
        WatchProgress finalProgress = ottService.updateProgress(resumedProgress.getId(), 2700L);
        System.out.println("Updated position: " + finalProgress.getPositionSeconds() + " seconds");
        System.out.println("Status: " + finalProgress.getStatus());

        // Get continue watching list
        System.out.println("\n--- Continue Watching List for User ---");
        List<WatchProgress> continueWatching = ottService.getContinueWatching(createdUser.getId());
        continueWatching.forEach(wp -> {
            Content content = contentService.getById(wp.getContentId());
            System.out.println("Content: " + content.getTitle());
            System.out.println("Position: " + wp.getPositionSeconds() + " seconds");
            System.out.println("Status: " + wp.getStatus());
            System.out.println("---");
        });

        System.out.println("\nUser journey completed successfully!");
    }
}
