package org.example.service.taskFilter;

import org.example.enums.FilterCriteria;
import org.example.model.Task;
import org.example.service.ITaskFilterStratergy;
import org.example.service.TaskService;

import java.util.List;

public class PriorityBasedTaskFilterFilterStratergy implements ITaskFilterStratergy {
    private TaskService taskService = new TaskService();

    @Override
    public boolean isApplicable(FilterCriteria filterCriteria) {
        return filterCriteria.equals(FilterCriteria.PRIORITY);
    }

    @Override
    public List<Task> getTasks(String id) {
        Integer priority = Integer.valueOf(id);
        return taskService.getTasksByPriority(priority);
    }
}
