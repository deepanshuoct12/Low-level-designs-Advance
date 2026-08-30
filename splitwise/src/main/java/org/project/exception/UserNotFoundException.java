package org.project.exception;

import org.project.constant.ErrorMessages;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
        super(String.format(ErrorMessages.USER_NOT_FOUND, userId));
    }
}
