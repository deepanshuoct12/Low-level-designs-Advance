package org.project.service;

import org.project.enums.Denomination;
import org.project.model.Cash;
import org.project.model.Receipt;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IATMApplication {
    List<Cash> withdrawMoney(Long accountId, BigDecimal amount, Long atmId);
    
    boolean validateUser(Long cardId, String pin);
    
    boolean depositMoney(Long inventoryId, Map<Denomination, Integer> denominations);
    
    Long checkBalance(Long accountId);
    
    boolean changePin(Long cardId, String oldPin, String newPin);
    
    Receipt getReceipt(Long transactionId);
}
