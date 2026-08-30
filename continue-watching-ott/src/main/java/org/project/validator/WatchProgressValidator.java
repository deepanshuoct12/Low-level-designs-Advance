package org.project.validator;

import org.project.constants.ErrorMessages;
import org.project.enums.WatchStatus;
import org.project.exception.InvalidInputException;
import org.project.exception.NotFoundException;
import org.project.exception.ValidationException;
import org.project.model.WatchProgress;

public class WatchProgressValidator {

    public static void validateForCreate(WatchProgress watchProgress) {
        if (watchProgress == null) {
            throw new ValidationException(ErrorMessages.WATCH_PROGRESS_CANNOT_BE_NULL);
        }
        validateUserId(watchProgress.getUserId());
        validateContentId(watchProgress.getContentId());
        validatePositionSeconds(watchProgress.getPositionSeconds());
        validateStatus(watchProgress.getStatus());
    }

    public static void validateForUpdate(Long id, WatchProgress watchProgress) {
        if (id == null || id <= 0) {
            throw new InvalidInputException(ErrorMessages.INVALID_WATCH_PROGRESS_ID);
        }
        if (watchProgress == null) {
            throw new ValidationException(ErrorMessages.WATCH_PROGRESS_CANNOT_BE_NULL);
        }
        validateUserId(watchProgress.getUserId());
        validateContentId(watchProgress.getContentId());
        validatePositionSeconds(watchProgress.getPositionSeconds());
        validateStatus(watchProgress.getStatus());
    }

    public static void validateExists(WatchProgress watchProgress) {
        if (watchProgress == null) {
            throw new NotFoundException(ErrorMessages.WATCH_PROGRESS_NOT_FOUND);
        }
    }

    public static void validatePositionUpdate(Long deltaSeconds) {
        if (deltaSeconds == null || deltaSeconds < 0) {
            throw new InvalidInputException(ErrorMessages.DELTA_SECONDS_MUST_BE_NON_NEGATIVE);
        }
    }

    private static void validateUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new InvalidInputException(ErrorMessages.USER_ID_MUST_BE_POSITIVE);
        }
    }

    private static void validateContentId(Long contentId) {
        if (contentId == null || contentId <= 0) {
            throw new InvalidInputException(ErrorMessages.CONTENT_ID_MUST_BE_POSITIVE);
        }
    }

    private static void validatePositionSeconds(Long positionSeconds) {
        if (positionSeconds == null || positionSeconds < 0) {
            throw new InvalidInputException(ErrorMessages.POSITION_SECONDS_MUST_BE_NON_NEGATIVE);
        }
    }

    private static void validateStatus(WatchStatus status) {
        if (status == null) {
            throw new ValidationException(ErrorMessages.STATUS_CANNOT_BE_NULL);
        }
    }
}
