package org.project.stratergy;

import org.project.enums.SplitType;
import org.project.model.Split;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AmountSplitStratergy implements ISplitStratergy {
    private static final double DELTA = 0.01;

    @Override
    public List<Split> calculateSplits(String expenseId, double totalAmount, Map<String, Double> splitValues) {
        if (splitValues == null || splitValues.isEmpty()) {
            throw new IllegalArgumentException("Amount split requires amount per user");
        }

        double totalSplitAmount = splitValues.values().stream().mapToDouble(Double::doubleValue).sum();
        if (Math.abs(totalSplitAmount - totalAmount) > DELTA) {
            throw new IllegalArgumentException("Split amounts must add up to " + totalAmount + ", but was " + totalSplitAmount);
        }

        List<Split> splits = new ArrayList<>();
        for (Map.Entry<String, Double> entry : splitValues.entrySet()) {
            Split split = new Split();
            split.setExpenseId(expenseId);
            split.setUserId(entry.getKey());
            split.setAmount(entry.getValue());
            split.setSplitType(SplitType.AMOUNT);
            splits.add(split);
        }
        return splits;
    }
}
