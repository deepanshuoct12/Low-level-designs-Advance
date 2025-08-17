package org.example.service;

import org.example.enums.FilterCriteria;
import org.example.model.Reminder;
import org.example.model.Task;
import org.example.enums.TaskStatus;
import lombok.extern.slf4j.Slf4j;
import org.example.service.taskFilter.DueDateBasedFilterStratergy;
import org.example.service.taskFilter.PriorityBasedTaskFilterFilterStratergy;
import org.example.service.taskFilter.UserBasesFilterTaskStratergy;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TaskManagementServiceImpl implements ITaskManagementService {
    private  static TaskManagementServiceImpl taskManagementService;
    private ReminderService reminderService = new ReminderService();
    private TaskService taskService = new TaskService();
    private static List<ITaskFilterStratergy> taskFilterStratergies = new ArrayList<>();
    
    private TaskManagementServiceImpl() {
    }

    public static ITaskManagementService getInstance() {
        if (taskManagementService == null) {
            synchronized (TaskManagementServiceImpl.class) {
                if (taskManagementService == null) {
                    taskManagementService = new TaskManagementServiceImpl();
                    taskFilterStratergies.add(new DueDateBasedFilterStratergy());
                    taskFilterStratergies.add(new PriorityBasedTaskFilterFilterStratergy());
                    taskFilterStratergies.add(new UserBasesFilterTaskStratergy());
                }
            }

            taskManagementService = new TaskManagementServiceImpl();
        }

        return taskManagementService;
    }

    @Override
    public void assignTask(String userId, String taskId) {
        if (userId == null || userId.trim().isEmpty() || taskId == null || taskId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID and Task ID cannot be null or empty");
        }
        
        Task task = taskService.getTask(taskId);
        if (task == null) {
            log.error("Task with ID {} not found", taskId);
            throw new IllegalArgumentException("Task not found");
        }
        
        task.setAssignedTo(userId);
        taskService.updateTask(taskId, task);
        log.info("Assigned task {} to user {}", taskId, userId);
    }

    @Override
    public void unassignTask(String userId, String taskId) {
        if (userId == null || userId.trim().isEmpty() || taskId == null || taskId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID and Task ID cannot be null or empty");
        }
        
        Task task = taskService.getTask(taskId);
        if (task == null) {
            log.error("Task with ID {} not found", taskId);
            throw new IllegalArgumentException("Task not found");
        }
        
        if (!userId.equals(task.getAssignedTo())) {
            log.warn("Task {} is not assigned to user {}", taskId, userId);
            throw new IllegalStateException("Task is not assigned to the specified user");
        }
        
        task.setAssignedTo(null);
        taskService.updateTask(taskId, task);
        log.info("Unassigned task {} from user {}", taskId, userId);
    }

    @Override
    public synchronized void markTaskComplete(String taskId) {
        if (taskId == null || taskId.trim().isEmpty()) {
            throw new IllegalArgumentException("Task ID cannot be null or empty");
        }
        
        Task task = taskService.getTask(taskId);
        if (task == null) {
            log.error("Task with ID {} not found", taskId);
            throw new IllegalArgumentException("Task not found");
        }
        
        task.setStatus(TaskStatus.COMPLETED);
        taskService.updateTask(taskId, task);
        log.info("Marked task {} as completed", taskId);
    }


    @Override
    public List<Task> searchTasks(String id, FilterCriteria filterCriteria) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Search ID cannot be null or empty");
        }

        return taskFilterStratergies.stream().filter(stratergy -> stratergy.isApplicable(filterCriteria)).findFirst().orElse(null).getTasks(id);
    }

    @Override
    public List<Task> viewHistory(String userId) {
        return taskService.getTasksByAssignedUser(userId);
    }

    @Override
    public void addReminder(String reminderId, String taskId) {
        Reminder reminder = reminderService.getReminder(reminderId);
        reminder.setTaskId(taskId);
        reminderService.addReminder(reminder);
    }
}
