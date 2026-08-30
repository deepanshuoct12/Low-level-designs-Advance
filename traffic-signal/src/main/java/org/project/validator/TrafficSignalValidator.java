package org.project.validator;

import org.apache.commons.lang3.StringUtils;
import org.project.constants.ErrorMessages;
import org.project.enums.Color;
import org.project.enums.Direction;
import org.project.exception.InvalidOperationException;

public final class TrafficSignalValidator {

    private TrafficSignalValidator() {
    }

    public static void validateIntersectionId(String intersectionId) {
        if (StringUtils.isBlank(intersectionId)) {
            throw new InvalidOperationException(ErrorMessages.INTERSECTION_ID_REQUIRED);
        }
    }

    public static void validateSignalId(String signalId) {
        if (StringUtils.isBlank(signalId)) {
            throw new InvalidOperationException(ErrorMessages.SIGNAL_ID_REQUIRED);
        }
    }

    public static void validateColor(Color color) {
        if (color == null) {
            throw new InvalidOperationException(ErrorMessages.COLOR_REQUIRED);
        }
    }

    public static void validateDirection(Direction direction) {
        if (direction == null) {
            throw new InvalidOperationException(ErrorMessages.DIRECTION_REQUIRED);
        }
    }

    public static void validateDuration(long duration) {
        if (duration <= 0) {
            throw new InvalidOperationException(ErrorMessages.DURATION_INVALID);
        }
    }
}
