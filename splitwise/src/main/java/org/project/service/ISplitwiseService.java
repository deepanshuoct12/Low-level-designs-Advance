package org.project.service;

import org.project.enums.SplitType;
import org.project.model.BalanceSheet;
import org.project.model.DebtSettlementSuggestion;
import org.project.model.Expense;
import org.project.model.Group;
import org.project.model.Transaction;

import java.util.List;
import java.util.Map;

public interface ISplitwiseService {
    Group createGroup(String name, String createdBy);

    void addMemberToGroup(String groupId, String userId);

    Expense addExpense(String groupId, String paidBy, double amount, String description,
                       SplitType splitType, Map<String, Double> splitValues);

    Transaction settleUp(String fromUserId, String toUserId, double amount);

    BalanceSheet getBalanceSheetBasedOnUserId(String userId);

    void deleteExpense(String expenseId);

    List<DebtSettlementSuggestion> simplifyDebts(String groupId);
}
