package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.dao.TaskDao;
import org.example.exception.TaskNotFoundException;
import org.example.model.Task;

import java.util.List;

@Slf4j
public class TaskService {
    private TaskDao taskDao = new TaskDao();

    public synchronized void addTask(Task task) {
        taskDao.addTask(task);
    }

    public synchronized void updateTask(String taskId,  Task task) {
        taskDao.updateTask(taskId, task);
    }

    public void deleteTask(String taskId) {
        taskDao.deleteTask(taskId);
    }

    public synchronized Task getTask(String taskId) {
        Task task = null;
        try {
            task = taskDao.getTask(taskId);
        } catch (TaskNotFoundException e) {
            log.error("Task not found", e);
        }

        return task;
    }

    public List<Task> getTasks() {
        return taskDao.getTasks();
    }

    /**
     * Fetches all tasks with the specified priority
     * @param priority The priority level to filter tasks by
     * @return List of tasks with the specified priority
     */
    public List<Task> getTasksByPriority(Integer priority) {
        if (priority == null) {
            throw new IllegalArgumentException("Priority cannot be null");
        }
        return taskDao.getTasksByPriority(priority);
    }

    /**
     * Fetches all tasks assigned to a specific user
     * @param assignedTo The user ID to filter tasks by
     * @return List of tasks assigned to the specified user
     */
    public List<Task> getTasksByAssignedUser(String assignedTo) {
        if (assignedTo == null || assignedTo.trim().isEmpty()) {
            throw new IllegalArgumentException("Assigned user cannot be null or empty");
        }
        return taskDao.getTasksByAssignedUser(assignedTo);
    }

    /**
     * Fetches all tasks due on a specific date
     * @param dateEpoch The date in epoch seconds to filter tasks by
     * @return List of tasks due on the specified date
     */
    public List<Task> getTasksByDueDate(Long dateEpoch) {
        if (dateEpoch == null || dateEpoch <= 0) {
            throw new IllegalArgumentException("Invalid date value");
        }
        return taskDao.getTasksByDate(dateEpoch);
    }

    /**
     * Fetches tasks based on multiple filter criteria
     * @param priority Optional priority filter
     * @param assignedTo Optional assigned user filter
     * @param dueDateEpoch Optional due date filter (in epoch seconds)
     * @return List of tasks matching all specified criteria
     */
    public List<Task> getTasksWithFilters(Integer priority, String assignedTo, Long dueDateEpoch) {
        // Start with all tasks
        List<Task> result = taskDao.getTasks();
        
        // Apply priority filter if specified
        if (priority != null) {
            result.retainAll(taskDao.getTasksByPriority(priority));
        }
        
        // Apply assigned user filter if specified
        if (assignedTo != null && !assignedTo.trim().isEmpty()) {
            result.retainAll(taskDao.getTasksByAssignedUser(assignedTo));
        }
        
        // Apply due date filter if specified
        if (dueDateEpoch != null && dueDateEpoch > 0) {
            result.retainAll(taskDao.getTasksByDate(dueDateEpoch));
        }
        
        return result;
    }
}
