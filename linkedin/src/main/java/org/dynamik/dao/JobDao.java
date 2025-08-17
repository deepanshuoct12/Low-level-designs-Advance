package org.dynamik.dao;

import org.dynamik.model.Job;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of Job data access object using an in-memory HashMap.
 * This class provides CRUD operations for Job entities and various search capabilities.
 */
public class JobDao implements IBaseDao<Job, String> {
    private static final Map<String, Job> jobs = new HashMap<>();

    @Override
    public Job save(Job job) {
        if (job == null) {
            throw new IllegalArgumentException("Job cannot be null");
        }
        if (job.getId() == null || job.getId().trim().isEmpty()) {
            job.setId("JOB_" + UUID.randomUUID().toString());
        }
        jobs.put(job.getId(), job);
        return job;
    }

    @Override
    public List<Job> findAll() {
        return new ArrayList<>(jobs.values());
    }

    @Override
    public void delete(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Job ID cannot be null or empty");
        }
        jobs.remove(id);
    }

    @Override
    public Job findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Job ID cannot be null or empty");
        }
        return jobs.get(id);
    }

    /**
     * Find jobs by company ID
     * @param companyId the ID of the company
     * @return list of jobs for the given company
     */
    public List<Job> findByCompanyId(String companyId) {
        if (companyId == null || companyId.trim().isEmpty()) {
            throw new IllegalArgumentException("Company ID cannot be null or empty");
        }
        return jobs.values().stream()
                .filter(job -> companyId.equalsIgnoreCase(job.getCompanyId()))
                .collect(Collectors.toList());
    }

    /**
     * Find jobs by recruiter ID
     * @param recruiterId the ID of the recruiter
     * @return list of jobs posted by the given recruiter
     */
    public List<Job> findByRecruiterId(String recruiterId) {
        if (recruiterId == null || recruiterId.trim().isEmpty()) {
            throw new IllegalArgumentException("Recruiter ID cannot be null or empty");
        }
        return jobs.values().stream()
                .filter(job -> recruiterId.equalsIgnoreCase(job.getRecruiterId()))
                .collect(Collectors.toList());
    }

    /**
     * Find jobs by title (case-insensitive partial match)
     * @param title the job title or part of it to search for
     * @return list of jobs matching the title
     */
    public List<Job> findByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        String searchTerm = title.toLowerCase();
        return jobs.values().stream()
                .filter(job -> job.getTitle().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    /**
     * Find jobs by location (case-insensitive partial match)
     * @param location the location to search for
     * @return list of jobs in the specified location
     */
    public List<Job> findByLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be null or empty");
        }
        String searchTerm = location.toLowerCase();
        return jobs.values().stream()
                .filter(job -> job.getLocation().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    /**
     * Find jobs by multiple criteria (title, location, company ID)
     * Any null or empty parameter will be ignored in the search
     * 
     * @param title the job title or part of it (optional)
     * @param location the job location or part of it (optional)
     * @param companyId the company ID (optional)
     * @return list of jobs matching all specified criteria
     */
//    public List<Job> searchJobs(String title, String location, String companyId) {
//        return jobs.values().stream()
//                .filter(job -> title == null || title.trim().isEmpty() ||
//                        job.getTitle().toLowerCase().contains(title.toLowerCase()))
//                .filter(job -> location == null || location.trim().isEmpty() ||
//                        job.getLocation().toLowerCase().contains(location.toLowerCase()))
//                .filter(job -> companyId == null || companyId.trim().isEmpty() ||
//                        companyId.equalsIgnoreCase(job.getCompanyId()))
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * Find jobs by multiple keywords in title or description
//     * @param keywords list of keywords to search for
//     * @return list of jobs containing any of the keywords in title or description
//     */
//    public List<Job> searchByKeywords(List<String> keywords) {
//        if (keywords == null || keywords.isEmpty()) {
//            return findAll();
//        }
//
//        return jobs.values().stream()
//                .filter(job -> keywords.stream()
//                        .anyMatch(keyword ->
//                            job.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
//                            (job.getDescription() != null &&
//                             job.getDescription().toLowerCase().contains(keyword.toLowerCase())
//                            )
//                        )
//                )
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * Search jobs by title or description (case-insensitive partial match)
//     * @param searchTerm the search term to look for in job titles and descriptions
//     * @return list of matching jobs
//     */
//    public List<Job> searchJobs(String searchTerm) {
//        if (searchTerm == null || searchTerm.trim().isEmpty()) {
//            return findAll();
//        }
//        String term = searchTerm.toLowerCase();
//        return jobs.values().stream()
//                .filter(job ->
//                    (job.getTitle() != null && job.getTitle().toLowerCase().contains(term)) ||
//                    (job.getDescription() != null && job.getDescription().toLowerCase().contains(term))
//                )
//                .collect(Collectors.toList());
//    }
}
