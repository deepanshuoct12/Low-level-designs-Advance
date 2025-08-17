package org.dynamik.demo;

import org.dynamik.enums.JobSearchTerm;
import org.dynamik.model.*;
import org.dynamik.service.CompanyService;
import org.dynamik.service.LinkedInApplication;
import org.dynamik.service.UserService;

import java.util.List;

/**
 * Class for demonstrating LinkedIn Application functionality.
 * Provides methods to showcase various features of the application.
 */
public class LinkedInApplicationDriver {
    private final LinkedInApplication linkedInApp;
    private final UserService userService;
    private final CompanyService companyService;

    /**
     * Constructor initializes the required services.
     */
    public LinkedInApplicationDriver() {
        this.linkedInApp = LinkedInApplication.getInstance();
        this.userService = new UserService();
        companyService = new CompanyService();
    }


    /**
     * Demonstrates the complete LinkedIn application flow including:
     * 1. User and company setup
     * 2. Job posting
     * 3. Job searching
     * 4. Job applications
     * 5. Social interactions (connections, messages, posts)
     */
    public void runDemo() {
        System.out.println("\n=== Starting LinkedIn Application Demo ===\n");

        // 1. Setup sample data
        System.out.println("=== 1. Setting up sample data ===");
//        Object[] sampleData = setupSampleData();
//        User john = (User) sampleData[0];
//        User jane = (User) sampleData[1];
//        User googleHr = (User) sampleData[2];
//        User microsoftHr = (User) sampleData[3];
//        Company google = (Company) sampleData[4];
//        Company microsoft = (Company) sampleData[5];

        // Create sample users
        User john = createUser("john_doe", "John Doe", "john@example.com");
        User jane = createUser("jane_smith", "Jane Smith", "jane@example.com");
        User googleHr = createUser("hr_google", "Google HR", "hr@google.com");
        User microsoftHr = createUser("hr_microsoft", "Microsoft HR", "hr@microsoft.com");

        // Create sample companies
        Company google = createCompany("GOOG", "Google", "Tech");
        Company microsoft = createCompany("MSFT", "Microsoft", "Tech");


        // 2. Post sample jobs
        System.out.println("\n=== 2. Posting sample jobs ===");
        postSampleJobs(googleHr, google, microsoftHr, microsoft);

        // 3. Demonstrate job search
        System.out.println("\n=== 3. Demonstrating job search ===");
        System.out.println("\nSearching for Software Engineer positions:");
        List<Job> softwareJobs = searchJobs("Software Engineer", JobSearchTerm.JOB_TITLE);

        System.out.println("\nSearching for jobs at Google:");
        List<Job> googleJobs = searchJobs("Google", JobSearchTerm.JOB_COMPANY);

        System.out.println("\nSearching for remote jobs:");
        List<Job> remoteJobs = searchJobs("Remote", JobSearchTerm.LOCATION);

        // 4. Demonstrate job applications
        System.out.println("\n=== 4. Demonstrating job applications ===");
        if (!softwareJobs.isEmpty()) {
            System.out.println("\nJohn is applying for a job:");
            applyForJob(john, softwareJobs.get(0).getId());

            System.out.println("\nJane is also applying for the same job:");
            applyForJob(jane, softwareJobs.get(0).getId());
        }

        // 5. Demonstrate social features
        System.out.println("\n=== 5. Demonstrating social features ===");

        // Connection requests
        System.out.println("\nJohn sends a connection request to Jane:");
        sendConnectionRequest(john, jane);

        System.out.println("\nJane accepts John's connection request:");
        acceptConnectionRequest(jane, john);

        // Messaging
        System.out.println("\nJohn sends a message to Jane:");
        sendMessage(john, jane, "Hi Jane, I see you're also looking for jobs. Let's connect!");

        System.out.println("\nJane replies to John:");
        sendMessage(jane, john, "Hi John! Yes, I'm exploring opportunities. Let's keep in touch!");

        // Posts and interactions
        System.out.println("\nJohn creates a post:");
        String johnsPostId = createPost(john, "Excited to start my job search journey! #NewBeginnings #JobHunt");

        System.out.println("\nJane likes John's post:");
        likePost(jane, johnsPostId);

        System.out.println("\nJane comments on John's post:");
        commentOnPost(jane, johnsPostId, "Best of luck with your job search, John!");

        System.out.println("\nJane creates her own post:");
        String janesPostId = createPost(jane, "Looking for opportunities in software development. #OpenToWork");

        System.out.println("\nJohn likes Jane's post:");
        likePost(john, janesPostId);

        System.out.println("\n=== LinkedIn Application Demo Completed Successfully ===\n");
    }


//    /**
//     * Creates sample users and companies for demonstration purposes.
//     * @return Array containing created users [user1, user2, recruiter1, recruiter2, google, microsoft]
//     */
//    public Object[] setupSampleData() {
//        System.out.println("=== Setting Up Sample Data ===\n");
//
//        // Create sample users
//        User user1 = createUser("john_doe", "John Doe", "john@example.com");
//        User user2 = createUser("jane_smith", "Jane Smith", "jane@example.com");
//        User recruiter1 = createUser("hr_google", "Google HR", "hr@google.com");
//        User recruiter2 = createUser("hr_microsoft", "Microsoft HR", "hr@microsoft.com");
//
//        // Create sample companies
//        Company google = createCompany("GOOG", "Google", "Tech");
//        Company microsoft = createCompany("MSFT", "Microsoft", "Tech");
//
//        return new Object[]{user1, user2, recruiter1, recruiter2, google, microsoft};
//    }

    /**
     * Creates a new user with the given details.
     * @param username Unique username for the user
     * @param name Full name of the user
     * @param email Email address of the user
     * @return The created User object
     */
    public User createUser(String username, String name, String email) {
        User user = new User();
        user.setId(username);
        user.setName(name);
        user.setEmail(email);
        userService.createUser(user);
        System.out.println("Created user: " + name);
        return user;
    }

    /**
     * Creates a new company with the given details.
     * @param id Company ID
     * @param name Company name
     * @param industry Industry sector
     * @return The created Company object
     */
    public Company createCompany(String id, String name, String industry) {
        // In a real app, you'd have a CompanyService
        Company company = new Company();
        company.setId(id);
        company.setName(name);
        company.setPictureUrl("https://example.com/" + id + ".png");
        companyService.createCompany(company);
        System.out.println("Created company: " + name);
        return company;
    }

    /**
     * Posts sample jobs for demonstration purposes.
     * @param googleRecruiter Recruiter from Google
     * @param google Google company
     * @param microsoftRecruiter Recruiter from Microsoft
     * @param microsoft Microsoft company
     */
    public void postSampleJobs(User googleRecruiter, Company google,
                                     User microsoftRecruiter, Company microsoft) {
        // Post Google jobs
        linkedInApp.postJob("Software Engineer", 
                          "Looking for experienced software engineers",
                          "careers.google.com/123", 
                          "Mountain View, CA", 
                          google.getId(), 
                          googleRecruiter.getId());
        
        linkedInApp.postJob("Product Manager", 
                          "Senior Product Manager position",
                          "careers.google.com/124", 
                          "Remote", 
                          google.getId(), 
                          googleRecruiter.getId());

        // Post Microsoft jobs
        linkedInApp.postJob("Software Developer", 
                          ".NET Developer with 3+ years experience",
                          "careers.microsoft.com/456", 
                          "Redmond, WA", 
                          microsoft.getId(), 
                          microsoftRecruiter.getId());
        
        System.out.println("Posted sample jobs for Google and Microsoft");
    }

    /**
     * Demonstrates job search functionality.
     * @param searchTerm The term to search for
     * @param searchType Type of search (title, company, location)
     * @return List of matching jobs
     */
    public List<Job> searchJobs(String searchType, JobSearchTerm searchTerm) {
        System.out.println("\nSearching for " + searchType + ": " + searchTerm);
        List<Job> results = linkedInApp.searchForJobs(searchTerm, searchType);
        if (results.isEmpty()) {
            System.out.println("No jobs found matching your criteria.");
        } else {
            System.out.println("Found " + results.size() + " jobs:");
            results.forEach(job -> System.out.println(" - " + job.getTitle() + " at " + 
                job.getCompanyId() + " (" + job.getLocation() + ")"));
        }
        return results;
    }

    /**
     * Applies for a job on behalf of a user.
     * @param user The user applying for the job
     * @param jobId ID of the job to apply for
     * @return true if application was successful, false otherwise
     */
    public boolean applyForJob(User user, String jobId) {
        System.out.println("\n" + user.getName() + " is applying for job " + jobId);
        try {
            linkedInApp.applyForJob(user.getId(), jobId);
            System.out.println("Application submitted successfully!");
            return true;
        } catch (Exception e) {
            System.out.println("Error applying for job: " + e.getMessage());
            return false;
        }
    }

    /**
     * Sends a connection request from one user to another.
     * @param from The user sending the request
     * @param to The user receiving the request
     */
    public void sendConnectionRequest(User from, User to) {
        System.out.println("\n" + from.getName() + " is sending a connection request to " + to.getName());
        linkedInApp.sendRequest(from.getId(), to.getId());
    }

    /**
     * Accepts a connection request.
     * @param user The user accepting the request
     * @param requester The user who sent the request
     */
    public void acceptConnectionRequest(User user, User requester) {
        System.out.println("\n" + user.getName() + " is accepting connection request from " + requester.getName());
        // In a real app, you'd need to get the actual connection ID
        // linkedInApp.acceptRequest(user.getId(), connectionId);
        System.out.println("Connection request accepted!");
    }

    /**
     * Sends a message from one user to another.
     * @param from Sender of the message
     * @param to Recipient of the message
     * @param message The message content
     */
    public void sendMessage(User from, User to, String message) {
        System.out.println("\n" + from.getName() + " is sending a message to " + to.getName() + ": " + message);
        linkedInApp.sendMessage(from.getId(), to.getId(), message);
    }

    /**
     * Creates a post on behalf of a user.
     * @param user The user creating the post
     * @param content The content of the post
     * @return The ID of the created post (simulated)
     */
    public String createPost(User user, String content) {
        System.out.println("\n" + user.getName() + " is creating a post: " + content);
        Post post = linkedInApp.doPost(user.getId(), "Post by " + user.getName(), content);
        // In a real implementation, return the actual post ID
        return post.getId();
    }

    /**
     * Simulates liking a post.
     * @param user The user liking the post
     * @param postId The ID of the post to like
     */
    public void likePost(User user, String postId) {
        System.out.println("\n" + user.getName() + " is liking post " + postId);
        linkedInApp.likePost(user.getId(), postId);
    }

    /**
     * Adds a comment to a post.
     * @param user The user adding the comment
     * @param postId The ID of the post to comment on
     * @param comment The comment text
     */
    public void commentOnPost(User user, String postId, String comment) {
        System.out.println("\n" + user.getName() + " is commenting on post " + postId + ": " + comment);
        linkedInApp.commentPost(user.getId(), postId, comment);
    }


}
