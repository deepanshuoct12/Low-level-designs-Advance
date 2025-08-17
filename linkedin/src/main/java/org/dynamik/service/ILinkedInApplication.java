package org.dynamik.service;

import org.dynamik.enums.JobSearchTerm;
import org.dynamik.model.Job;
import org.dynamik.model.Post;

import java.util.List;

public interface ILinkedInApplication {
     void sendRequest(String senderId, String receiverId);
     void acceptRequest(String userId, String conectionId);
     void rejectRequest(String userId, String conectionId);
     void sendMessage(String senderId, String receiverId, String message);
     void likePost(String userId, String postId);
     void commentPost(String userId, String postId, String comment);
     List<Job> searchForJobs(JobSearchTerm searchTerm, String searchType);
     void applyForJob(String userId, String jobId);
     void postJob(String jobTitle, String jobDescription, String applyUrl, String location, String companyId, String recruiterId);
     Post doPost(String userId, String postTitle, String postDescription);
     List<Post> getPostsByUserId(String userId);   // fetch feed for a user
}
