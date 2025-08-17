package org.example.dao;

import org.example.exception.ReminderNotFoundException;
import org.example.model.Reminder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object for managing Reminder entities.
 * Uses an in-memory list for storage and maintains an ID index for faster lookups.
 */
public class ReminderDao {
    private final List<Reminder> reminders = new ArrayList<>();
    private final Map<String, Reminder> idIndex = new HashMap<>();

    /**
     * Retrieves all reminders in the system.
     * @return A new list containing all reminders
     */
    public List<Reminder> getReminders() {
        return new ArrayList<>(reminders); // Return a copy to prevent external modification
    }

    /**
     * Adds a new reminder to the system.
     * @param reminder The reminder to add
     * @throws IllegalArgumentException if the reminder is null or has no ID
     */
    public void addReminder(Reminder reminder) {
        if (reminder == null) {
            throw new IllegalArgumentException("Reminder cannot be null");
        }
        if (reminder.getId() == null || reminder.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Reminder ID cannot be null or empty");
        }
        
        reminders.add(reminder);
        idIndex.put(reminder.getId(), reminder);
    }

    /**
     * Removes a reminder from the system.
     * @param reminder The reminder to remove
     * @return true if the reminder was found and removed, false otherwise
     */
    public boolean removeReminder(Reminder reminder) {
        if (reminder == null || reminder.getId() == null) {
            return false;
        }
        
        boolean removed = reminders.remove(reminder);
        if (removed) {
            idIndex.remove(reminder.getId());
        }
        return removed;
    }
    
    /**
     * Removes a reminder by its ID.
     * @param reminderId The ID of the reminder to remove
     * @return true if the reminder was found and removed, false otherwise
     */
    public boolean removeReminder(String reminderId) {
        if (reminderId == null || reminderId.trim().isEmpty()) {
            return false;
        }
        
        Reminder reminder = idIndex.get(reminderId);
        if (reminder != null) {
            reminders.remove(reminder);
            idIndex.remove(reminderId);
            return true;
        }
        return false;
    }

    /**
     * Removes all reminders from the system.
     */
    public void clearReminders() {
        reminders.clear();
        idIndex.clear();
    }
    
    /**
     * Updates an existing reminder.
     * @param reminder The reminder with updated information
     * @throws ReminderNotFoundException if the reminder is not found
     */
    public void updateReminder(Reminder reminder) {
        if (reminder == null || reminder.getId() == null) {
            throw new IllegalArgumentException("Reminder and its ID cannot be null");
        }
        
        Reminder existing = idIndex.get(reminder.getId());
        if (existing == null) {
            throw new ReminderNotFoundException("Reminder with ID " + reminder.getId() + " not found");
        }
        
        // Update the existing reminder's fields
        int index = reminders.indexOf(existing);
        if (index != -1) {
            reminders.set(index, reminder);
        }
        
        // Update the index
        idIndex.put(reminder.getId(), reminder);
    }
    
    /**
     * Finds a reminder by its ID.
     * @param id The ID of the reminder to find
     * @return The found reminder, or null if not found
     */
    public Reminder findById(String id) {
        return idIndex.get(id);
    }
    
    /**
     * Finds all reminders for a specific task.
     * @param taskId The ID of the task
     * @return A list of reminders for the specified task
     */
    public List<Reminder> findByTaskId(String taskId) {
        List<Reminder> result = new ArrayList<>();
        for (Reminder reminder : reminders) {
            if (taskId.equals(reminder.getTaskId())) {
                result.add(reminder);
            }
        }
        return result;
    }

    public Reminder getReminder(String id) {
        return idIndex.get(id);
    }

    public List<Reminder> getRemindersByTaskId(String taskId) {
        return idIndex.values().stream()
                .filter(reminder -> taskId.equals(reminder.getTaskId()))
                .toList();
    }
}
