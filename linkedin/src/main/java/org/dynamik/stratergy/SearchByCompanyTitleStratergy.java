package org.dynamik.stratergy;

import org.dynamik.enums.JobSearchTerm;
import org.dynamik.model.Job;
import org.dynamik.service.JobService;

import java.util.List;

public class SearchByCompanyTitleStratergy implements IJobSearchStratergy {
    private JobService jobService = new JobService();

    @Override
    public Boolean isApplicable(JobSearchTerm jobSearchTerm) {
        return jobSearchTerm.equals(JobSearchTerm.JOB_TITLE);
    }

    @Override
    public List<Job> getJobs(String searchTerm) {
        return jobService.getJobsByTitle(searchTerm);
    }
}
