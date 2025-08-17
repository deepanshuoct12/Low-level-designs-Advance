package org.example.demo;

import org.example.enums.FilterCriteria;
import org.example.model.Task;
import org.example.model.Reminder;
import org.example.service.ITaskManagementService;
import org.example.service.TaskManagementServiceImpl;
import org.example.service.TaskService;
import org.example.service.ReminderService;
import org.example.enums.TaskStatus;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Driver class to demonstrate the Task Management System functionality.
 * Creates dummy users and tasks, and showcases all available operations.
 */
public class TaskManageMentDriver {

    // Dummy user IDs
    private static final String USER_1 = "user1@example.com";
    private static final String USER_2 = "user2@example.com";
    private static final String USER_3 = "user3@example.com";

    private final ITaskManagementService taskManagementService;
    private final TaskService taskService;
    private final ReminderService reminderService;

    public TaskManageMentDriver() {
        this.taskManagementService = TaskManagementServiceImpl.getInstance();
        this.taskService = new TaskService();
        this.reminderService = new ReminderService();
    }

    // Public demo method that can be called from test class
    public void runDemo() {
        try {
            System.out.println("=== Starting Task Management System Demo ===\n");

            // Create and display sample tasks
            System.out.println("=== Creating Sample Tasks ===");
            String task1 = createSampleTask("Complete project documentation", "Write detailed API documentation", 2, USER_1);
            String task2 = createSampleTask("Fix login bug", "Investigate and fix authentication issue", 1, USER_2);
            String task3 = createSampleTask("Performance optimization", "Optimize database queries", 3, USER_1);

            // Display all tasks
            System.out.println("\n=== All Tasks ===");
            displayAllTasks();

            // Assign tasks to users
            System.out.println("\n=== Assigning Tasks ===");
            assignTask(USER_1, task1);
            assignTask(USER_2, task2);
            assignTask(USER_1, task3);  // User 1 gets another task

            // Search and display tasks by user
            System.out.println("\n=== Tasks Assigned to " + USER_1 + " ===");
            searchTasks(USER_1, FilterCriteria.USER).stream().forEach(task -> System.out.println(task.getTitle()));

            // Mark a task as complete
            System.out.println("\n=== Completing Task ===");
            completeTask(task2);

            // Unassign a task
            System.out.println("\n=== Unassigning Task ===");
            unassignTask(USER_1, task3);

            // Assign it to another user
            System.out.println("\n=== Reassigning Task ===");
            assignTask(USER_3, task3);

            // Search by priority
            System.out.println("\n=== High Priority Tasks (Priority 1) ===");
            searchTasks("1", FilterCriteria.PRIORITY).stream().forEach(task -> System.out.println(task.getTitle()));;

            // Search by due date (using today's date)
            System.out.println("\n=== Tasks Due Today ===");
            long todayEpoch = getDueDate().toInstant().toEpochMilli() / 1000;
            searchTasks(String.valueOf(todayEpoch), FilterCriteria.DUE_DATE).stream().forEach(task -> System.out.println(task.getTitle()));;

            // Display final state of all tasks
            System.out.println("\n=== Final State of All Tasks ===");
            displayAllTasks();

            System.out.println("\n=== Demo Completed Successfully ===");

        } catch (Exception e) {
            System.err.println("Error during demo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Creates a sample task with the given details
     */
    /**
     * Creates a sample task with the given details
     * @param title Task title
     * @param description Task description
     * @param priority Task priority (1-3, where 1 is highest)
     * @param createdBy User ID of the task creator
     * @return The ID of the created task
     */
    public String createSampleTask(String title, String description, int priority, String createdBy) {
        Task task = new Task();
        task.setId(UUID.randomUUID().toString());
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(TaskStatus.PENDING);
        task.setPriority(priority);
        task.setDueDate(getDueDate());
        task.setCreatedBy(createdBy);

        taskService.addTask(task);
        System.out.println("Created task: " + title + " (ID: " + task.getId() + ")");
        return task.getId();
    }

    /**
     * Assigns a task to a user
     */
    public void assignTask(String userId, String taskId) {
        System.out.println("Assigning task " + taskId + " to user " + userId);
        taskManagementService.assignTask(userId, taskId);
        System.out.println("Successfully assigned task " + taskId + " to " + userId);
    }

    /**
     * Unassigns a task from a user
     */
    public void unassignTask(String userId, String taskId) {
        System.out.println("Unassigning task " + taskId + " from user " + userId);
        taskManagementService.unassignTask(userId, taskId);
        System.out.println("Successfully unassigned task " + taskId);
    }

    /**
     * Marks a task as complete
     */
    public void completeTask(String taskId) {
        System.out.println("Marking task " + taskId + " as complete");
        taskManagementService.markTaskComplete(taskId);
        System.out.println("Successfully marked task " + taskId + " as complete");
    }

    /**
     * Searches for tasks based on the given criteria
     */
    public List<Task> searchTasks(String id, FilterCriteria filterCriteria) {
        System.out.println("Searching for tasks with criteria: " + id);
        return taskManagementService.searchTasks(id, filterCriteria);
    }

    /**
     * Displays all tasks in the system
     */
    /**
     * Creates a new reminder for a task
     * @param message The reminder message
     * @param taskId The ID of the task to set the reminder for
     * @param minutesFromNow Minutes from now when the reminder should trigger
     * @return The created reminder ID
     */
    public String createReminder(String message, String taskId, long minutesFromNow) {
        Reminder reminder = new Reminder();
        String reminderId = UUID.randomUUID().toString();
        
        reminder.setId(reminderId);
        reminder.setMessage(message);
        reminder.setTaskId(taskId);
        
        // Set reminder time to current time + specified minutes
        long reminderTime = System.currentTimeMillis() + (minutesFromNow * 60 * 1000);
        reminder.setTime(reminderTime / 1000); // Convert to seconds for storage
        
        reminderService.addReminder(reminder);
        System.out.println("Created reminder: " + message + " (ID: " + reminderId + ")");
        return reminderId;
    }
    
    /**
     * Displays all reminders for a specific task
     * @param taskId The ID of the task
     */
    public void displayTaskReminders(String taskId) {
        List<Reminder> reminders = reminderService.findByTaskId(taskId);
        System.out.println("\n=== Reminders for Task " + taskId + " ===");
        
        if (reminders.isEmpty()) {
            System.out.println("No reminders found for this task.");
            return;
        }
        
        System.out.println(String.format("%-20s %-30s %-25s %s", 
            "Reminder ID", "Message", "Time", "Status"));
        System.out.println("-".repeat(100));
        
        long now = System.currentTimeMillis() / 1000; // Current time in seconds
        
        for (Reminder reminder : reminders) {
            String status = (reminder.getTime() <= now) ? "DUE" : "PENDING";
            String timeStr = new Date(reminder.getTime() * 1000L).toString();
            
            System.out.println(String.format("%-20s %-30s %-25s %s",
                reminder.getId().substring(0, 8) + "...",
                reminder.getMessage().substring(0, Math.min(25, reminder.getMessage().length())) + 
                    (reminder.getMessage().length() > 25 ? "..." : ""),
                timeStr,
                status
            ));
        }
    }
    
    /**
     * Deletes a reminder by its ID
     * @param reminderId The ID of the reminder to delete
     * @return true if the reminder was found and deleted, false otherwise
     */
    public boolean deleteReminder(String reminderId) {
        Reminder reminder = reminderService.getReminder(reminderId);
        if (reminder != null) {
            reminderService.removeReminder(reminder);
            System.out.println("Deleted reminder: " + reminderId);
            return true;
        }
        System.out.println("Reminder not found: " + reminderId);
        return false;
    }
    
    /**
     * Updates an existing reminder
     * @param reminderId The ID of the reminder to update
     * @param newMessage The new message (null to keep existing)
     * @param newTimeMinutes New time in minutes from now (0 or negative to keep existing)
     */
    public void updateReminder(String reminderId, String newMessage, long newTimeMinutes) {
        Reminder reminder = reminderService.getReminder(reminderId);
        if (reminder == null) {
            System.out.println("Reminder not found: " + reminderId);
            return;
        }
        
        if (newMessage != null) {
            reminder.setMessage(newMessage);
        }
        
        if (newTimeMinutes > 0) {
            long newTime = System.currentTimeMillis() + (newTimeMinutes * 60 * 1000);
            reminder.setTime(newTime / 1000);
        }
        
        reminderService.updateReminder(reminder);
        System.out.println("Updated reminder: " + reminderId);
    }
    
    /**
     * Displays all reminders in the system
     */
    public void displayAllReminders() {
        List<Reminder> reminders = reminderService.getReminders();
        System.out.println("\n=== All Reminders ===");
        
        if (reminders.isEmpty()) {
            System.out.println("No reminders found.");
            return;
        }
        
        System.out.println(String.format("%-20s %-20s %-30s %-25s %s", 
            "Reminder ID", "Task ID", "Message", "Time", "Status"));
        System.out.println("-".repeat(130));
        
        long now = System.currentTimeMillis() / 1000; // Current time in seconds
        
        for (Reminder reminder : reminders) {
            String status = (reminder.getTime() <= now) ? "DUE" : "PENDING";
            String timeStr = new Date(reminder.getTime() * 1000L).toString();
            String taskId = (reminder.getTaskId() != null) ? 
                reminder.getTaskId().substring(0, 8) + "..." : "N/A";
            
            System.out.println(String.format("%-20s %-20s %-30s %-25s %s",
                reminder.getId().substring(0, 8) + "...",
                taskId,
                reminder.getMessage().substring(0, Math.min(25, reminder.getMessage().length())) + 
                    (reminder.getMessage().length() > 25 ? "..." : ""),
                timeStr,
                status
            ));
        }
    }
    
    public void displayAllTasks() {
        System.out.println("\nCurrent Tasks in the System:");
        System.out.println(String.format("%-20s %-25s %-12s %-15s %-8s %s",
                "ID", "Title", "Status", "Assigned To", "Priority", "Due Date"));
        System.out.println("-".repeat(100));

        taskService.getTasks().forEach(task -> {
            System.out.println(String.format("%-20s %-25s %-12s %-15s %-8d %tF",
                    task.getId().substring(0, 8) + "...",
                    task.getTitle().substring(0, Math.min(20, task.getTitle().length())) +
                            (task.getTitle().length() > 20 ? "..." : ""),
                    task.getStatus(),
                    task.getAssignedTo() != null ?
                            task.getAssignedTo().substring(0, Math.min(12, task.getAssignedTo().length())) : "-",
                    task.getPriority(),
                    task.getDueDate()
            ));
        });
    }

    private Date getDueDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }
}
