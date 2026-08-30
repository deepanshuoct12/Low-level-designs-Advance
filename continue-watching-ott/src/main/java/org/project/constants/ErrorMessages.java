package org.project.constants;

public class ErrorMessages {
    // Content validation messages
    public static final String CONTENT_CANNOT_BE_NULL = "Content cannot be null";
    public static final String TITLE_CANNOT_BE_NULL_OR_EMPTY = "Title cannot be null or empty";
    public static final String TITLE_TOO_LONG = "Title cannot exceed 200 characters";
    public static final String DESCRIPTION_CANNOT_BE_NULL_OR_EMPTY = "Description cannot be null or empty";
    public static final String DESCRIPTION_TOO_LONG = "Description cannot exceed 1000 characters";
    public static final String DURATION_MUST_BE_POSITIVE = "Duration must be a positive number";
    public static final String CONTENT_TYPE_CANNOT_BE_NULL = "Content type cannot be null";
    public static final String GENRE_CANNOT_BE_NULL = "Genre cannot be null";
    public static final String INVALID_CONTENT_ID = "Invalid content ID";

    // WatchProgress validation messages
    public static final String WATCH_PROGRESS_CANNOT_BE_NULL = "WatchProgress cannot be null";
    public static final String WATCH_PROGRESS_NOT_FOUND = "WatchProgress not found";
    public static final String INVALID_WATCH_PROGRESS_ID = "Invalid watch progress ID";
    public static final String USER_ID_MUST_BE_POSITIVE = "User ID must be a positive number";
    public static final String CONTENT_ID_MUST_BE_POSITIVE = "Content ID must be a positive number";
    public static final String POSITION_SECONDS_MUST_BE_NON_NEGATIVE = "Position seconds must be a non-negative number";
    public static final String DELTA_SECONDS_MUST_BE_NON_NEGATIVE = "Delta seconds must be a non-negative number";
    public static final String STATUS_CANNOT_BE_NULL = "Status cannot be null";
}
