package org.dynamik.service;

import org.dynamik.dao.JobApplicationDao;
import org.dynamik.model.JobApplication;

import java.util.List;


public class JobApplicationService {
    private final JobApplicationDao jobApplicationDao;

    public JobApplicationService() {
        this.jobApplicationDao = new JobApplicationDao();
    }

    /**
     * Creates a new job application
     * @param jobId The ID of the job being applied for
     * @param applicantId The ID of the applicant
     * @param recruiterId The ID of the recruiter
     * @param coverLetter Optional cover letter
     * @return The created job application
     */
    public JobApplication createJobApplication(String jobId, String applicantId, String recruiterId, String coverLetter) {
        if (jobId == null || jobId.trim().isEmpty()) {
            throw new IllegalArgumentException("Job ID cannot be null or empty");
        }
        if (applicantId == null || applicantId.trim().isEmpty()) {
            throw new IllegalArgumentException("Applicant ID cannot be null or empty");
        }
        if (recruiterId == null || recruiterId.trim().isEmpty()) {
            throw new IllegalArgumentException("Recruiter ID cannot be null or empty");
        }

        JobApplication application = new JobApplication();
        application.setJobId(jobId);
        application.setApplicantId(applicantId);
        application.setRecruiterId(recruiterId);
        application.setCoverLetter(coverLetter);
        
        return jobApplicationDao.save(application);
    }

    /**
     * Retrieves a job application by ID
     * @param applicationId The ID of the application to retrieve
     * @return The job application, or empty if not found
     */
    public JobApplication getJobApplication(String applicationId) {
        if (applicationId == null || applicationId.trim().isEmpty()) {
            throw new IllegalArgumentException("Application ID cannot be null or empty");
        }
        return jobApplicationDao.findById(applicationId);
    }

    /**
     * Gets all applications for a specific job
     * @param jobId The ID of the job
     * @return List of job applications for the specified job
     */
    public List<JobApplication> getApplicationsByJobId(String jobId) {
        if (jobId == null || jobId.trim().isEmpty()) {
            throw new IllegalArgumentException("Job ID cannot be null or empty");
        }
        return jobApplicationDao.findByJobId(jobId);
    }

    /**
     * Gets all applications by a specific applicant
     * @param applicantId The ID of the applicant
     * @return List of job applications by the specified applicant
     */
    public List<JobApplication> getApplicationsByApplicantId(String applicantId) {
        if (applicantId == null || applicantId.trim().isEmpty()) {
            throw new IllegalArgumentException("Applicant ID cannot be null or empty");
        }
        return jobApplicationDao.findByApplicantId(applicantId);
    }

    /**
     * Updates the status of a job application
     * @param applicationId The ID of the application to update
     * @param status The new status (e.g., "PENDING", "REVIEWED", "ACCEPTED", "REJECTED")
     * @return The updated job application
     * @throws IllegalArgumentException if the application doesn't exist or status is invalid
     */
    public JobApplication updateApplicationStatus(String applicationId, String status) {
        if (applicationId == null || applicationId.trim().isEmpty()) {
            throw new IllegalArgumentException("Application ID cannot be null or empty");
        }
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }

        JobApplication application = jobApplicationDao.findById(applicationId);
        if (application == null) {
            throw new IllegalArgumentException("Application not found with ID: " + applicationId);

        }

        application.setStatus(status);
        return jobApplicationDao.update(application);
    }

    /**
     * Gets all job applications (for admin/reporting purposes)
     * @return List of all job applications
     */
    public List<JobApplication> getAllApplications() {
        return jobApplicationDao.findAll();
    }
}
