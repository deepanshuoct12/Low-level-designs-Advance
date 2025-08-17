package org.example.service;

import org.example.enums.FilterCriteria;
import org.example.model.Reminder;
import org.example.model.Task;

import java.util.List;

public interface ITaskManagementService {
   public void assignTask(String userId, String taskId);
   public void unassignTask(String userId, String taskId);
   public void markTaskComplete(String taskId);
   public List<Task> searchTasks(String id, FilterCriteria filter); // it can be priority, assigned user or due date

   public List<Task> viewHistory(String userId);


   /*
      I am assuming reminders and task are created seperately and then linking has been done for them
    */
   public void addReminder(String reminderId, String taskId);
}
