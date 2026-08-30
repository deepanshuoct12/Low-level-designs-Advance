package org.project.exception;

import org.project.constant.ErrorMessages;

public class ExpenseNotFoundException extends RuntimeException {
    public ExpenseNotFoundException(String expenseId) {
        super(String.format(ErrorMessages.EXPENSE_NOT_FOUND, expenseId));
    }
}
