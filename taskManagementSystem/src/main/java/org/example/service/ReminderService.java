package org.example.service;

import org.example.dao.ReminderDao;
import org.example.model.Reminder;

import java.util.List;

public class ReminderService {
private ReminderDao reminderDao = new ReminderDao();

 public void addReminder(Reminder reminder) {
    reminderDao.addReminder(reminder);
 }

 public void removeReminder(Reminder reminder) {
    reminderDao.removeReminder(reminder);
 }

 public void updateReminder(Reminder reminder) {
    reminderDao.updateReminder(reminder);
 }

 public Reminder getReminder(String id) {
    return reminderDao.getReminder(id);
 }

 public void clearReminders() {
    reminderDao.clearReminders();
 }

 public List<Reminder> getReminders() {
    return reminderDao.getReminders();
 }

 public List<Reminder> findByTaskId(String taskId) {
    return reminderDao.findByTaskId(taskId);
 }

}
