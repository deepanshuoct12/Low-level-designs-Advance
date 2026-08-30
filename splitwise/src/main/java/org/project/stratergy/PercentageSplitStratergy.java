package org.project.stratergy;

import org.project.enums.SplitType;
import org.project.model.Split;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PercentageSplitStratergy implements ISplitStratergy {
    private static final double DELTA = 0.01;

    @Override
    public List<Split> calculateSplits(String expenseId, double totalAmount, Map<String, Double> splitValues) {
        if (splitValues == null || splitValues.isEmpty()) {
            throw new IllegalArgumentException("Percentage split requires percentage per user");
        }

        double totalPercentage = splitValues.values().stream().mapToDouble(Double::doubleValue).sum();
        if (Math.abs(totalPercentage - 100.0) > DELTA) {
            throw new IllegalArgumentException("Percentages must add up to 100, but was " + totalPercentage);
        }

        List<Split> splits = new ArrayList<>();
        for (Map.Entry<String, Double> entry : splitValues.entrySet()) {
            Split split = new Split();
            split.setExpenseId(expenseId);
            split.setUserId(entry.getKey());
            split.setAmount(totalAmount * entry.getValue() / 100.0);
            split.setSplitType(SplitType.PERCENTAGE);
            splits.add(split);
        }
        return splits;
    }
}
