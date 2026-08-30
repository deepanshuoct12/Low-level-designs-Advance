package org.project.exception;

import org.project.constant.ErrorMessages;

public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException(String groupId) {
        super(String.format(ErrorMessages.GROUP_NOT_FOUND, groupId));
    }
}
