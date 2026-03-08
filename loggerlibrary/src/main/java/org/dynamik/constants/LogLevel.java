package org.dynamik.constants;

public enum LogLevel {
     DEBUG(1),
     INFO(2),
     ERROR(3),
     WARN(4),
     FATAL(5);

    private int priority;

     LogLevel(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
