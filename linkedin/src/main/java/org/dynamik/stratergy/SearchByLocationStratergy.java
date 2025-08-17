package org.dynamik.stratergy;

import org.dynamik.enums.JobSearchTerm;
import org.dynamik.model.Job;
import org.dynamik.service.JobService;

import java.util.List;

public class SearchByLocationStratergy implements IJobSearchStratergy {
    private JobService jobService = new JobService();

    @Override
    public Boolean isApplicable(JobSearchTerm jobSearchTerm) {
        return jobSearchTerm.equals(JobSearchTerm.LOCATION);
    }

    @Override
    public List<Job> getJobs(String searchTerm) {
        return jobService.getJobsByLocation(searchTerm);
    }
}
