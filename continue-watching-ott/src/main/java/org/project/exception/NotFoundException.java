package org.project.exception;

import org.project.constants.ErrorCodes;

public class NotFoundException extends BaseException {
    public NotFoundException(String message) {
        super(ErrorCodes.NOT_FOUND, message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(ErrorCodes.NOT_FOUND, message, cause);
    }
}
