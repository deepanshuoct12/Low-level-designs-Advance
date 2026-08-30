package org.project.constants;

public final class ErrorMessages {

    private ErrorMessages() {
    }

    public static final String INTERSECTION_ID_REQUIRED = "Intersection id must not be null or empty";
    public static final String SIGNAL_ID_REQUIRED = "Signal id must not be null or empty";
    public static final String COLOR_REQUIRED = "Color must not be null";
    public static final String DIRECTION_REQUIRED = "Direction must not be null";
    public static final String DURATION_INVALID = "Timer duration must be greater than zero";

    public static final String INTERSECTION_NOT_FOUND = "Intersection not found for id: %s";
    public static final String SIGNAL_NOT_FOUND = "Signal not found for intersection: %s and direction: %s";
    public static final String INTERSECTION_ALREADY_RUNNING = "Intersection is already running: %s";
    public static final String INTERSECTION_NOT_RUNNING = "Intersection is not running: %s";
}
