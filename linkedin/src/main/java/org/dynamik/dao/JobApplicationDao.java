package org.dynamik.dao;

import org.dynamik.model.JobApplication;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class JobApplicationDao implements IBaseDao<JobApplication, String> {
    private final Map<String, JobApplication> applications = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1000); // Start IDs from 1000

    @Override
    public JobApplication save(JobApplication application) {
        if (application.getId() == null || application.getId().isEmpty()) {
            // New application - generate ID
            String newId = "APP" + idCounter.incrementAndGet();
            application.setId(newId);
        }
        
        // Set application date if not set
        if (application.getAppliedDate() == null) {
            application.setAppliedDate(LocalDateTime.now());
        }
        
        // Set default status if not set
        if (application.getStatus() == null || application.getStatus().isEmpty()) {
            application.setStatus("PENDING");
        }
        
        applications.put(application.getId(), application);
        return application;
    }

    @Override
    public JobApplication findById(String id) {
        if (id == null || !applications.containsKey(id)) {
            return null;
        }

        return applications.get(id);
    }


    public List<JobApplication> findByJobId(String jobId) {
        return applications.values().stream()
                .filter(app -> jobId.equals(app.getJobId()))
                .collect(Collectors.toList());
    }


    public List<JobApplication> findByApplicantId(String applicantId) {
        return applications.values().stream()
                .filter(app -> applicantId.equals(app.getApplicantId()))
                .collect(Collectors.toList());
    }


    public JobApplication update(JobApplication application) {
        if (application.getId() == null || !applications.containsKey(application.getId())) {
            throw new IllegalArgumentException("Cannot update non-existent application");
        }
        applications.put(application.getId(), application);
        return application;
    }

    @Override
    public void delete(String id) {
        applications.remove(id);
    }

    @Override
    public List<JobApplication> findAll() {
        return new ArrayList<>(applications.values());
    }
}
