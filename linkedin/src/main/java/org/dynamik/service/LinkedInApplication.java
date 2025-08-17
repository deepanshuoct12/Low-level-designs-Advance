package org.dynamik.service;

import org.dynamik.enums.ConnectionRequestState;
import org.dynamik.enums.JobSearchTerm;
import org.dynamik.enums.NotificationType;
import org.dynamik.model.*;
import org.dynamik.observer.NotificationSubject;
import org.dynamik.observer.UserObserver;
import org.dynamik.stratergy.IJobSearchStratergy;
import org.dynamik.stratergy.SearchByCompanyNameSearchStratergy;
import org.dynamik.stratergy.SearchByCompanyTitleStratergy;
import org.dynamik.stratergy.SearchByLocationStratergy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LinkedInApplication extends NotificationSubject implements ILinkedInApplication{

    private ConnectionService connectionService = new ConnectionService();
    private NotificationService notificationService = new NotificationService();
    private MessageService messageService =  new MessageService();
    private PostService postService = new PostService();
    private LikeService likeService = new LikeService();
    private UserService userService = new UserService();
    private CommentService commentService = new CommentService();
    private JobService jobService = new JobService();
    private JobApplicationService jobApplicationService = new JobApplicationService();
    private static List<IJobSearchStratergy> jobStratergies = new ArrayList<>();
    private static LinkedInApplication linkedInApplication;

    private LinkedInApplication() {

    }

    public static LinkedInApplication getInstance() {
        if (linkedInApplication == null) {
            synchronized (LinkedInApplication.class) {
                if (linkedInApplication == null) {
                    linkedInApplication = new LinkedInApplication();
                    jobStratergies.add(new SearchByCompanyNameSearchStratergy());
                    jobStratergies.add(new SearchByCompanyTitleStratergy());
                    jobStratergies.add(new SearchByLocationStratergy());
                }
            }
        }

        return linkedInApplication;
    }

    public void registerObserver(User user) {
        UserObserver observer = new UserObserver(user);
        addObserver(observer);
    }

    public void registerObservers(List<User> user) {
        for (User u : user) {
            UserObserver observer = new UserObserver(u);
            addObserver(observer);
        }
    }

    @Override
    public void sendRequest(String senderId, String receiverId) {
       Connection connection = new Connection();
       connection.setState(ConnectionRequestState.PENDING);
       connection.setUserId(senderId);
       connection.setConnectionId(receiverId);
       connectionService.sendConnectionRequest(connection);
       Notification notification = notificationService.createNotification(receiverId, "New connection request", NotificationType.CONNECTION_REQUEST);
       clearObservers();
       registerObserver(userService.getUserById(receiverId));
       notifyObservers(notification);
    }

    @Override
    public void acceptRequest(String userId, String connectionId) {
      connectionService.acceptConnection(connectionId);
    }

    @Override
    public void rejectRequest(String userId, String connectionId) {
      connectionService.rejectConnection(connectionId);
    }

    @Override
    public void sendMessage(String senderId, String receiverId, String message) {
        messageService.createMessage(new Message(message, senderId, receiverId));
        Notification notification = notificationService.createNotification(receiverId, message, NotificationType.CONNECTION_REQUEST);
        notifyObservers(notification);
    }

    @Override
    public void likePost(String userId, String postId) {
      likeService.addLike(new Like(postId, userId));
      Post post = postService.getPostById(postId);
      User user = userService.getUserById(userId);
      Notification notification = notificationService.createNotification(userId, post.getTitle(), NotificationType.LIKE);
      clearObservers();
      registerObserver(user);
      notifyObservers(notification);
    }

    @Override
    public void commentPost(String userId, String postId, String comment) {
        commentService.addComment(new Comment(postId, userId, comment));
        Post post = postService.getPostById(postId);
        User user = userService.getUserById(post.getUserId());
        Notification notification = notificationService.createNotification(userId, post.getTitle(), NotificationType.COMMENT);
        clearObservers();
        registerObserver(user);
        notifyObservers(notification);
    }

    @Override
    public List<Job> searchForJobs(JobSearchTerm searchTerm, String searchType) {
       List<Job> jobs = jobStratergies.stream().filter(stratergy -> stratergy.isApplicable(searchTerm)).findAny().get().getJobs(searchType);
       return jobs;
    }

    @Override
    public void applyForJob(String userId, String jobId) {
        // Get user and job details
        User applicant = userService.getUserById(userId);
        Job job = jobService.getJobById(jobId);
        User recruiter = userService.getUserById(job.getRecruiterId());
        
        try {
            // Create and save job application
            JobApplication application = jobApplicationService.createJobApplication(
                jobId,
                userId,
                job.getRecruiterId(),
                "" // Empty cover letter for now, could be added as a parameter
            );
            
            // Notify the recruiter about the job application
            String recruiterMessage = String.format("%s has applied for the position: %s (Application ID: %s)", 
                applicant.getName(), 
                job.getTitle(),
                application.getId());
                
            Notification recruiterNotification = notificationService.createNotification(
                job.getRecruiterId(), 
                recruiterMessage,
                NotificationType.JOB
            );
            
            // Notify the applicant that their application was received
            String applicantMessage = String.format("You've successfully applied for: %s at %s (Application ID: %s)", 
                job.getTitle(), 
                job.getCompanyId(),
                application.getId());
                
            Notification applicantNotification = notificationService.createNotification(
                userId,
                applicantMessage,
                NotificationType.JOB
            );
            
            // Set up observers and send notifications
            clearObservers();
            registerObserver(recruiter);
            notifyObservers(recruiterNotification);
            
            clearObservers();
            registerObserver(applicant);
            notifyObservers(applicantNotification);
            
        } catch (IllegalArgumentException e) {
            // Log the error and rethrow or handle it appropriately
            System.err.println("Error applying for job: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void postJob(String jobTitle, String jobDescription, String applyUrl, String location, String companyId, String recruiterId) {
      Job job = jobService.postJob(new Job(jobTitle, jobDescription, applyUrl, location, companyId, recruiterId));
      Notification notification = notificationService.createNotification(recruiterId, job.getTitle(), NotificationType.JOB);
      notifyObservers(notification);
    }

    @Override
    public Post doPost(String userId, String postTitle, String postDescription) {
       return postService.createPost(new Post(userId, postTitle, postDescription));
    }

    @Override
    public List<Post> getPostsByUserId(String userId) {
        List<Connection> connections = connectionService.getConnectionsByUser(userId); // Replace with your own method>
        List<String> connectionUserIds = connections.stream().map(Connection::getUserId).collect(Collectors.toList());
        return postService.getPostsByUser(connectionUserIds);
    }
}
