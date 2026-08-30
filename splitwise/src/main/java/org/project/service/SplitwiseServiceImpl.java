package org.project.service;

import org.project.constant.ErrorMessages;
import org.project.enums.SplitType;
import org.project.enums.TransactionStatus;
import org.project.exception.ExpenseNotFoundException;
import org.project.exception.GroupNotFoundException;
import org.project.exception.InvalidOperationException;
import org.project.exception.UserNotFoundException;
import org.project.model.BalanceSheet;
import org.project.model.DebtSettlementSuggestion;
import org.project.model.Expense;
import org.project.model.Group;
import org.project.model.Split;
import org.project.model.Transaction;
import org.project.model.User;
import org.project.observer.Subject;
import org.project.stratergy.AmountSplitStratergy;
import org.project.stratergy.ISplitStratergy;
import org.project.stratergy.PercentageSplitStratergy;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplitwiseServiceImpl extends Subject implements ISplitwiseService {
    private static final double DELTA = 0.01;
    private static  SplitwiseServiceImpl instance;

    private final Map<SplitType, ISplitStratergy> stratergies = new EnumMap<>(SplitType.class);

    private final UserService userService = new UserService();
    private final GroupService groupService = new GroupService();
    private final ExpenseService expenseService = new ExpenseService();
    private final SplitService splitService = new SplitService();
    private final TransactionService transactionService = new TransactionService();
    private final BalanceSheetService balanceSheetService = new BalanceSheetService();

    private SplitwiseServiceImpl() {
        stratergies.put(SplitType.PERCENTAGE, new PercentageSplitStratergy());
        stratergies.put(SplitType.AMOUNT, new AmountSplitStratergy());
    }

    public static SplitwiseServiceImpl getInstance() {
        if (instance == null) {
            synchronized (SplitwiseServiceImpl.class) {
                if (instance == null) {
                    instance = new SplitwiseServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public Group createGroup(String name, String createdBy) {
        User creator = userService.getById(createdBy);
        if (creator == null) {
            throw new UserNotFoundException(createdBy);
        }

        Group group = new Group();
        group.setName(name);
        group.setCreatedBy(createdBy);
        group.setMemberIds(new ArrayList<>(List.of(createdBy)));
        groupService.create(group);

        addObserver(creator);
        return group;
    }

    @Override
    public void addMemberToGroup(String groupId, String userId) {
        Group group = groupService.getById(groupId);
        if (group == null) {
            throw new GroupNotFoundException(groupId);
        }
        User user = userService.getById(userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }

        if (group.getMemberIds() == null) {
            group.setMemberIds(new ArrayList<>());
        }
        if (!group.getMemberIds().contains(userId)) {
            group.getMemberIds().add(userId);
            groupService.update(group);
            addObserver(user);
        }
    }

    @Override
    public Expense addExpense(String groupId, String paidBy, double amount, String description,
                              SplitType splitType, Map<String, Double> splitValues) {
        Group group = groupService.getById(groupId);
        if (group == null) {
            throw new GroupNotFoundException(groupId);
        }
        if (!group.getMemberIds().contains(paidBy)) {
            throw new InvalidOperationException(String.format(ErrorMessages.PAYER_NOT_GROUP_MEMBER, paidBy));
        }
        if (amount <= 0) {
            throw new InvalidOperationException(ErrorMessages.EXPENSE_AMOUNT_INVALID);
        }
        for (String userId : splitValues.keySet()) {
            if (!group.getMemberIds().contains(userId)) {
                throw new InvalidOperationException(String.format(ErrorMessages.USER_NOT_GROUP_MEMBER, userId));
            }
        }

        ISplitStratergy stratergy = stratergies.get(splitType);
        if (stratergy == null) {
            throw new InvalidOperationException(String.format(ErrorMessages.SPLIT_STRATEGY_NOT_FOUND, splitType));
        }

        Expense expense = new Expense();
        expense.setGroupId(groupId);
        expense.setPaidBy(paidBy);
        expense.setAmount(amount);
        expense.setDescription(description);
        expenseService.create(expense);

        List<Split> splits = stratergy.calculateSplits(expense.getId(), amount, splitValues);
        for (Split split : splits) {
            splitService.create(split);
            applyBalance(paidBy, split.getUserId(), split.getAmount());
        }

        notifyObservers(expense);
        return expense;
    }

    @Override
    public Transaction settleUp(String fromUserId, String toUserId, double amount) {
        if (amount <= 0) {
            throw new InvalidOperationException(ErrorMessages.SETTLE_UP_AMOUNT_INVALID);
        }
        if (fromUserId.equals(toUserId)) {
            throw new InvalidOperationException(ErrorMessages.SETTLE_UP_SELF);
        }

        Transaction transaction = new Transaction();
        transaction.setFromUserId(fromUserId);
        transaction.setToUserId(toUserId);
        transaction.setAmount(amount);
        transaction.setStatus(TransactionStatus.PENDING);
        transactionService.create(transaction);

        try {
            applyBalance(fromUserId, toUserId, amount);
            transaction.setStatus(TransactionStatus.COMPLETED);
            transactionService.update(transaction);
        } catch (RuntimeException e) {
            transaction.setStatus(TransactionStatus.FAILED);
            transactionService.update(transaction);
            throw e;
        }

        return transaction;
    }

    @Override
    public BalanceSheet getBalanceSheetBasedOnUserId(String userId) {
        return balanceSheetService.getByUserId(userId);
    }

    @Override
    public void deleteExpense(String expenseId) {
        Expense expense = expenseService.getById(expenseId);
        if (expense == null) {
            throw new ExpenseNotFoundException(expenseId);
        }

        for (Split split : getSplitsByExpenseId(expenseId)) {
            applyBalance(expense.getPaidBy(), split.getUserId(), -split.getAmount());
            splitService.delete(split.getId());
        }
        expenseService.delete(expenseId);
    }

    @Override
    public List<DebtSettlementSuggestion> simplifyDebts(String groupId) {
        Group group = groupService.getById(groupId);
        if (group == null) {
            throw new GroupNotFoundException(groupId);
        }

        List<String> members = group.getMemberIds();
        Map<String, Double> netAmounts = new HashMap<>();
        for (String member : members) {
            Map<String, Double> balances = getBalanceSheetBasedOnUserId(member).getBalances();
            double net = 0.0;
            for (String other : members) {
                if (!other.equals(member)) {
                    net += balances.getOrDefault(other, 0.0);
                }
            }
            netAmounts.put(member, net);
        }

        List<DebtSettlementSuggestion> settlements = new ArrayList<>();
        while (true) {
            String maxCreditor = null;
            String maxDebtor = null;
            for (Map.Entry<String, Double> entry : netAmounts.entrySet()) {
                if (maxCreditor == null || entry.getValue() > netAmounts.get(maxCreditor)) {
                    maxCreditor = entry.getKey();
                }
                if (maxDebtor == null || entry.getValue() < netAmounts.get(maxDebtor)) {
                    maxDebtor = entry.getKey();
                }
            }
            if (maxCreditor == null || netAmounts.get(maxCreditor) < DELTA) {
                break;
            }

            double amount = Math.min(netAmounts.get(maxCreditor), -netAmounts.get(maxDebtor));
            netAmounts.put(maxCreditor, netAmounts.get(maxCreditor) - amount);
            netAmounts.put(maxDebtor, netAmounts.get(maxDebtor) + amount);

            DebtSettlementSuggestion suggestion = new DebtSettlementSuggestion();
            suggestion.setFromUserId(maxDebtor);
            suggestion.setToUserId(maxCreditor);
            suggestion.setAmount(amount);
            settlements.add(suggestion);
        }
        return settlements;
    }

    private List<Split> getSplitsByExpenseId(String expenseId) {
        List<Split> splits = new ArrayList<>();
        for (Split split : splitService.getAll()) {
            if (expenseId.equals(split.getExpenseId())) {
                splits.add(split);
            }
        }
        return splits;
    }

    private void applyBalance(String creditorId, String debtorId, double amount) {
        if (creditorId.equals(debtorId)) {
            return;
        }

        BalanceSheet creditorSheet = getBalanceSheetBasedOnUserId(creditorId);
        creditorSheet.getBalances().merge(debtorId, amount, Double::sum);
        balanceSheetService.update(creditorSheet);

        BalanceSheet debtorSheet = getBalanceSheetBasedOnUserId(debtorId);
        debtorSheet.getBalances().merge(creditorId, -amount, Double::sum);
        balanceSheetService.update(debtorSheet);
    }
}
