package org.project.exception;

import org.project.constants.ErrorCodes;

public class ValidationException extends BaseException {
    public ValidationException(String message) {
        super(ErrorCodes.VALIDATION_ERROR, message);
    }

    public ValidationException(String message, Throwable cause) {
        super(ErrorCodes.VALIDATION_ERROR, message, cause);
    }
}
