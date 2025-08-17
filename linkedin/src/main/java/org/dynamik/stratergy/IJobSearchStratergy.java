package org.dynamik.stratergy;

import org.dynamik.enums.JobSearchTerm;
import org.dynamik.model.Job;

import java.util.List;

public interface IJobSearchStratergy {
    Boolean isApplicable(JobSearchTerm jobSearchTerm);
    List <Job> getJobs(String searchTerm);
}
