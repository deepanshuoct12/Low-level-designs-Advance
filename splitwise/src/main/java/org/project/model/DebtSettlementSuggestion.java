package org.project.model;

import lombok.Data;

@Data
public class DebtSettlementSuggestion {
    private String fromUserId;
    private String toUserId;
    private double amount;
}
