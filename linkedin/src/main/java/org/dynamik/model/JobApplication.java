package org.dynamik.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobApplication {
    private String id;
    private String jobId;
    private String applicantId;
    private String recruiterId;
    private String status; // e.g., "PENDING", "REVIEWED", "ACCEPTED", "REJECTED"
    private LocalDateTime appliedDate;
    private String coverLetter; // Optional cover letter

    // Constructors
    public JobApplication() {
        this.appliedDate = LocalDateTime.now();
        this.status = "PENDING";
    }

    public JobApplication(String jobId, String applicantId, String recruiterId, String coverLetter) {
        this.jobId = jobId;
        this.applicantId = applicantId;
        this.recruiterId = recruiterId;
        this.coverLetter = coverLetter;
        this.appliedDate = LocalDateTime.now();
        this.status = "PENDING";
    }
}
