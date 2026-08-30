package org.project.validator;

import org.project.constants.ErrorMessages;
import org.project.exception.InvalidInputException;
import org.project.exception.ValidationException;
import org.project.model.Content;
import org.project.enums.ContentType;
import org.project.enums.Genre;

public class ContentValidator {

    public static void validateForCreate(Content content) {
        if (content == null) {
            throw new ValidationException(ErrorMessages.CONTENT_CANNOT_BE_NULL);
        }
        validateTitle(content.getTitle());
        validateDescription(content.getDescription());
        validateDuration(content.getDuration());
        validateContentType(content.getContentType());
        validateGenre(content.getGenre());
    }

    public static void validateForUpdate(Long id, Content content) {
        if (id == null || id <= 0) {
            throw new InvalidInputException(ErrorMessages.INVALID_CONTENT_ID);
        }
        if (content == null) {
            throw new ValidationException(ErrorMessages.CONTENT_CANNOT_BE_NULL);
        }
        validateTitle(content.getTitle());
        validateDescription(content.getDescription());
        validateDuration(content.getDuration());
        validateContentType(content.getContentType());
        validateGenre(content.getGenre());
    }

    private static void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new ValidationException(ErrorMessages.TITLE_CANNOT_BE_NULL_OR_EMPTY);
        }
        if (title.length() > 200) {
            throw new ValidationException(ErrorMessages.TITLE_TOO_LONG);
        }
    }

    private static void validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new ValidationException(ErrorMessages.DESCRIPTION_CANNOT_BE_NULL_OR_EMPTY);
        }
        if (description.length() > 1000) {
            throw new ValidationException(ErrorMessages.DESCRIPTION_TOO_LONG);
        }
    }

    private static void validateDuration(Long duration) {
        if (duration == null || duration <= 0) {
            throw new InvalidInputException(ErrorMessages.DURATION_MUST_BE_POSITIVE);
        }
    }

    private static void validateContentType(ContentType contentType) {
        if (contentType == null) {
            throw new ValidationException(ErrorMessages.CONTENT_TYPE_CANNOT_BE_NULL);
        }
    }

    private static void validateGenre(Genre genre) {
        if (genre == null) {
            throw new ValidationException(ErrorMessages.GENRE_CANNOT_BE_NULL);
        }
    }
}
