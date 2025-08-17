package org.example.service;

import org.example.enums.FilterCriteria;
import org.example.model.Task;

import java.util.List;

public interface ITaskFilterStratergy {
    public boolean isApplicable(FilterCriteria  filterCriteria);
    public List<Task> getTasks(String id);
}
