package org.dynamik.service;

import org.dynamik.dao.JobDao;
import org.dynamik.model.Job;

import java.util.List;
import java.util.Objects;

public class JobService {
    private final JobDao jobDao;

    public JobService() {
        this.jobDao = new JobDao();
    }

    public Job postJob(Job job) {
        return jobDao.save(job);
    }

    public Job getJobById(String jobId) {
        return jobDao.findById(jobId);
    }

    public List<Job> getAllJobs() {
        return jobDao.findAll();
    }

    public List<Job> getJobsByCompany(String companyId) {
        return jobDao.findByCompanyId(companyId);
    }
    public List<Job> getJobsByTitle(String title) {
        return jobDao.findByTitle(title);
    }
    public List<Job> getJobsByLocation(String locationId) {
        return jobDao.findByLocation(locationId);
    }

    public List<Job> getJobsByRecruiter(String recruiterId) {
        return jobDao.findByRecruiterId(recruiterId);
    }

//    public List<Job> searchJobs(String searchTerm) {
//        return jobDao.searchJobs(searchTerm);
//    }
    public void deleteJob(String jobId) {
        jobDao.delete(jobId);
    }
}
