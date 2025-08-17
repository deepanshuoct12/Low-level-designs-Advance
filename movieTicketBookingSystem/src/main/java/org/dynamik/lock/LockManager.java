package org.dynamik.lock;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class LockManager {
    private final Map<String, ReentrantLock> locks = new ConcurrentHashMap<>();

    public void lock(String id) {
        synchronized(this) {
            locks.putIfAbsent(id, new ReentrantLock());
        }
        locks.get(id).lock();
    }

    public void unlock(String id) {
        ReentrantLock lock = locks.get(id);
        if (lock != null && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    public void lockAll(String... ids) {
        // Sort to prevent deadlocks
        String[] sortedIds = ids.clone();
        Arrays.sort(sortedIds);

        for (String id : sortedIds) {
            lock(id);
        }
    }

    // Helper method to unlock multiple resources
    public void unlockAll(String... ids) {
        // Unlock in reverse order (good practice)
        for (int i = ids.length - 1; i >= 0; i--) {
            unlock(ids[i]);
        }
    }
}
