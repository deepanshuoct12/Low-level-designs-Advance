package org.project.constant;

public final class ErrorMessages {
    private ErrorMessages() {
    }

    public static final String USER_NOT_FOUND = "User not found: %s";
    public static final String GROUP_NOT_FOUND = "Group not found: %s";
    public static final String EXPENSE_NOT_FOUND = "Expense not found: %s";

    public static final String PAYER_NOT_GROUP_MEMBER = "Payer is not a member of the group: %s";
    public static final String USER_NOT_GROUP_MEMBER = "User is not a member of the group: %s";

    public static final String EXPENSE_AMOUNT_INVALID = "Expense amount must be positive";
    public static final String SETTLE_UP_AMOUNT_INVALID = "Settle up amount must be positive";
    public static final String SETTLE_UP_SELF = "Cannot settle up with yourself";

    public static final String SPLIT_STRATEGY_NOT_FOUND = "No split stratergy registered for: %s";
}
