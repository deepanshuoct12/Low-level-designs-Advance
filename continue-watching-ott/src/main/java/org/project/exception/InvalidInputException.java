package org.project.exception;

import org.project.constants.ErrorCodes;

public class InvalidInputException extends BaseException {
    public InvalidInputException(String message) {
        super(ErrorCodes.INVALID_INPUT, message);
    }

    public InvalidInputException(String message, Throwable cause) {
        super(ErrorCodes.INVALID_INPUT, message, cause);
    }
}
