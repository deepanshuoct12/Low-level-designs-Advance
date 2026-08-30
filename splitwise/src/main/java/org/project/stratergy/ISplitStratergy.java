package org.project.stratergy;

import org.project.model.Split;

import java.util.List;
import java.util.Map;

public interface ISplitStratergy {
    List<Split> calculateSplits(String expenseId, double totalAmount, Map<String, Double> splitValues);
}
