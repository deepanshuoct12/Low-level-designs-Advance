package org.dynamik.service;

import org.dynamik.model.Message;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class ScheduledDeletionService {
    
    private final MessageService messageService;
    private final Timer timer;
    private final AtomicBoolean isRunning;
    
    public ScheduledDeletionService(MessageService messageService) {
        this.messageService = messageService;
        this.timer = new Timer("MessageCleanupTimer", true); // Daemon thread
        this.isRunning = new AtomicBoolean(false);
    }
    
    public void start() {
        if (isRunning.compareAndSet(false, true)) {
            // Schedule to run every 30 seconds
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    deleteExpiredMessages();
                }
            }, 30000, 30000); // Initial delay
            System.out.println("⏰ Message deletion scheduler started - runs every 60 seconds");
        }
    }
    
    private void deleteExpiredMessages() {
        try {
            long currentTime = System.currentTimeMillis();
            
            // Get all messages and delete expired ones
            List<Message> allMessages = messageService.findAll();
            int deletedCount = 0;
            
            for (Message message : allMessages) {
                if (message.getDeletionTime() != null && message.getDeletionTime() <= currentTime) {
                    messageService.delete(message);
                    deletedCount++;
                    System.out.println("🗑️  Deleted expired message: " + message.getContent());
                }
            }
            
            if (deletedCount > 0) {
                System.out.println("🧹 Cleanup completed: " + deletedCount + " messages deleted");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error during message cleanup: " + e.getMessage());
        }
    }
    
    public void stop() {
        if (isRunning.compareAndSet(true, false)) {
            timer.cancel();
            System.out.println("⏹️  Message deletion scheduler stopped");
        }
    }
}
