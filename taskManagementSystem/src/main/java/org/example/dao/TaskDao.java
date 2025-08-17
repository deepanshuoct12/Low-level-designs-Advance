package org.example.dao;

import org.example.exception.TaskNotFoundException;
import org.example.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskDao {
    private static List<Task> tasks = new ArrayList<>();
    private static HashMap<String, Task> idIndex = new HashMap<>();
    private static HashMap<Long, List<Task>> dateIndex = new HashMap<>();
    private static HashMap<Integer, List<Task>> priorityIndex = new HashMap<>();
    private static HashMap<String, List<Task>> assignedUserIndex = new HashMap<>();
    
    public Task getTaskById(String id) {
        return idIndex.get(id);
    }
    
    public List<Task> getTasksByDate(Long dateEpoch) {
        return dateIndex.getOrDefault(dateEpoch, new ArrayList<>());
    }
    
    public List<Task> getTasksByPriority(Integer priority) {
        return priorityIndex.getOrDefault(priority, new ArrayList<>());
    }

    public void addTask(Task task) {
        // Add to tasks list
        tasks.add(task);
        
        // Index by ID
        idIndex.put(task.getId(), task);
        
        // Index by date (using epoch seconds)
        Long dateKey = task.getDueDateInEpoch();
        dateIndex.computeIfAbsent(dateKey, k -> new ArrayList<>()).add(task);
        
        // Index by priority
        Integer priorityKey = task.getPriority();
        priorityIndex.computeIfAbsent(priorityKey, k -> new ArrayList<>()).add(task);
        
        // Index by assigned user
        String assignedTo = task.getAssignedTo();
        if (assignedTo != null && !assignedTo.trim().isEmpty()) {
            assignedUserIndex.computeIfAbsent(assignedTo.trim().toLowerCase(), k -> new ArrayList<>()).add(task);
        }
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }
    
    /**
     * Retrieves all tasks assigned to a specific user
     * @param assignedTo The user ID to filter tasks by
     * @return List of tasks assigned to the specified user (case-insensitive match)
     */
    public List<Task> getTasksByAssignedUser(String assignedTo) {
        if (assignedTo == null || assignedTo.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(assignedUserIndex.getOrDefault(assignedTo.trim().toLowerCase(), new ArrayList<>()));
    }

    public Task getTask(String taskId) {
        if (idIndex.get(taskId) == null) {
            throw new TaskNotFoundException("Task not found");
        }
        return idIndex.get(taskId);
    }

    public void deleteTask(String taskId) {
        Task task = getTask(taskId);
        tasks.remove(task);
        idIndex.remove(taskId);
    }

    public void updateTask(String taskId, Task updatedTask) {
        Task existingTask = getTask(taskId);
        
        // Store old values for index updates
        Long oldDateKey = existingTask.getDueDateInEpoch();
        Integer oldPriorityKey = existingTask.getPriority();
        String oldAssignedTo = existingTask.getAssignedTo() != null ? 
                             existingTask.getAssignedTo().trim().toLowerCase() : null;
        
        // Update task fields
        updateTaskFields(existingTask, updatedTask);
        
        // Update indexes if needed
        updateDateIndex(existingTask, oldDateKey);
        updatePriorityIndex(existingTask, oldPriorityKey);
        updateAssignedToIndex(existingTask, oldAssignedTo);
    }
    
    private void updateTaskFields(Task existingTask, Task updatedTask) {
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setDueDate(updatedTask.getDueDate());
        existingTask.setPriority(updatedTask.getPriority());
        existingTask.setAssignedTo(updatedTask.getAssignedTo());
    }
    
    private void updateDateIndex(Task task, Long oldDateKey) {
        Long newDateKey = task.getDueDateInEpoch();
        if (!newDateKey.equals(oldDateKey)) {
            removeFromDateIndex(oldDateKey, task);
            addToDateIndex(task, newDateKey);
        }
    }
    
    private void updatePriorityIndex(Task task, Integer oldPriorityKey) {
        Integer newPriorityKey = task.getPriority();
        if (!newPriorityKey.equals(oldPriorityKey)) {
            removeFromPriorityIndex(oldPriorityKey, task);
            addToPriorityIndex(task, newPriorityKey);
        }
    }
    
    private void removeFromDateIndex(Long dateKey, Task task) {
        List<Task> dateTasks = dateIndex.get(dateKey);
        if (dateTasks != null) {
            dateTasks.remove(task);
            if (dateTasks.isEmpty()) {
                dateIndex.remove(dateKey);
            }
        }
    }
    
    private void addToDateIndex(Task task, Long dateKey) {
        dateIndex.computeIfAbsent(dateKey, k -> new ArrayList<>()).add(task);
    }
    
    private void removeFromPriorityIndex(Integer priority, Task task) {
        List<Task> priorityTasks = priorityIndex.get(priority);
        if (priorityTasks != null) {
            priorityTasks.remove(task);
            if (priorityTasks.isEmpty()) {
                priorityIndex.remove(priority);
            }
        }
    }
    
    private void addToPriorityIndex(Task task, Integer priority) {
        priorityIndex.computeIfAbsent(priority, k -> new ArrayList<>()).add(task);
    }
    
    private void updateAssignedToIndex(Task task, String oldAssignedTo) {
        String newAssignedTo = task.getAssignedTo() != null ? 
                             task.getAssignedTo().trim().toLowerCase() : null;
        
        // If assignedTo hasn't changed, no need to update index
        if ((oldAssignedTo == null && newAssignedTo == null) || 
            (oldAssignedTo != null && oldAssignedTo.equals(newAssignedTo))) {
            return;
        }
        
        // Remove from old assignedTo index if it exists
        if (oldAssignedTo != null) {
            List<Task> oldUserTasks = assignedUserIndex.get(oldAssignedTo);
            if (oldUserTasks != null) {
                oldUserTasks.removeIf(t -> t.getId().equals(task.getId()));
                if (oldUserTasks.isEmpty()) {
                    assignedUserIndex.remove(oldAssignedTo);
                }
            }
        }
        
        // Add to new assignedTo index if newAssignedTo is not null or empty
        if (newAssignedTo != null && !newAssignedTo.isEmpty()) {
            assignedUserIndex.computeIfAbsent(newAssignedTo, k -> new ArrayList<>()).add(task);
        }
    }

}
