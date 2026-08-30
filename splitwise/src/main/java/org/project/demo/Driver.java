package org.project.demo;

import org.project.enums.SplitType;
import org.project.model.BalanceSheet;
import org.project.model.DebtSettlementSuggestion;
import org.project.model.Expense;
import org.project.model.Group;
import org.project.model.Transaction;
import org.project.model.User;
import org.project.service.ISplitwiseService;
import org.project.service.SplitwiseServiceImpl;
import org.project.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Driver {
    public void runDemo() {
        UserService userService = new UserService();
        ISplitwiseService splitwiseService = SplitwiseServiceImpl.getInstance();

        User alice = new User();
        alice.setName("Alice");
        alice.setEmail("alice@example.com");
        userService.create(alice);
        System.out.println("Created user: " + alice.getName() + " (id=" + alice.getId() + ")");

        User bob = new User();
        bob.setName("Bob");
        bob.setEmail("bob@example.com");
        userService.create(bob);
        System.out.println("Created user: " + bob.getName() + " (id=" + bob.getId() + ")");

        User charlie = new User();
        charlie.setName("Charlie");
        charlie.setEmail("charlie@example.com");
        userService.create(charlie);
        System.out.println("Created user: " + charlie.getName() + " (id=" + charlie.getId() + ")");

        User dave = new User();
        dave.setName("Dave");
        dave.setEmail("dave@example.com");
        userService.create(dave);
        System.out.println("Created user: " + dave.getName() + " (id=" + dave.getId() + ")");

        Group group = splitwiseService.createGroup("Goa Trip", alice.getId());
        System.out.println("Created group: " + group.getName() + " (id=" + group.getId() + ")");

        splitwiseService.addMemberToGroup(group.getId(), bob.getId());
        splitwiseService.addMemberToGroup(group.getId(), charlie.getId());
        splitwiseService.addMemberToGroup(group.getId(), dave.getId());
        System.out.println("Added Bob, Charlie, Dave to group " + group.getName());

        Map<String, Double> splitValues = new HashMap<>();
        splitValues.put(alice.getId(), 25.0);
        splitValues.put(bob.getId(), 25.0);
        splitValues.put(charlie.getId(), 25.0);
        splitValues.put(dave.getId(), 25.0);

        Expense expense = splitwiseService.addExpense(group.getId(), alice.getId(), 4000.0,
                "Hotel booking", SplitType.PERCENTAGE, splitValues);
        System.out.println("Added expense: " + expense.getDescription() + " amount=" + expense.getAmount()
                + " paidBy=" + alice.getName());

        System.out.println("Balances after expense:");
        logBalanceSheet(splitwiseService, alice);
        logBalanceSheet(splitwiseService, bob);
        logBalanceSheet(splitwiseService, charlie);
        logBalanceSheet(splitwiseService, dave);

        Transaction transaction = splitwiseService.settleUp(bob.getId(), alice.getId(), 1000.0);
        System.out.println("Settled up: " + bob.getName() + " -> " + alice.getName()
                + " amount=" + transaction.getAmount() + " status=" + transaction.getStatus());

        System.out.println("Balances after settle up:");
        logBalanceSheet(splitwiseService, alice);
        logBalanceSheet(splitwiseService, bob);
        logBalanceSheet(splitwiseService, charlie);
        logBalanceSheet(splitwiseService, dave);

        List<DebtSettlementSuggestion> suggestions = splitwiseService.simplifyDebts(group.getId());
        System.out.println("Simplified debts:");
        for (DebtSettlementSuggestion suggestion : suggestions) {
            System.out.println(suggestion.getFromUserId() + " -> " + suggestion.getToUserId()
                    + " amount=" + suggestion.getAmount());
        }
    }

    private void logBalanceSheet(ISplitwiseService splitwiseService, User user) {
        BalanceSheet balanceSheet = splitwiseService.getBalanceSheetBasedOnUserId(user.getId());
        System.out.println("  " + user.getName() + ": " + balanceSheet.getBalances());
    }
}
