package org.project.validator;

import org.project.model.Account;
import org.project.model.PaymentIntent;

import java.math.BigDecimal;

public class TransactionValidator {

    private TransactionValidator() {
    }

    public static boolean isBalanceSufficient(Account senderAccount, PaymentIntent paymentIntent) {
        if (!isValidAccount(senderAccount) || !isValidPaymentIntent(paymentIntent)) {
            return false;
        }
        return senderAccount.getBalance().compareTo(paymentIntent.getAmount()) >= 0;
    }

    public static boolean isValidAccount(Account account) {
        return account != null
                && account.getId() != null && !account.getId().isEmpty()
                && account.getBalance() != null;
    }

    public static boolean isValidPaymentIntent(PaymentIntent paymentIntent) {
        return paymentIntent != null
                && paymentIntent.getId() != null && !paymentIntent.getId().isEmpty()
                && isValidAmount(paymentIntent.getAmount());
    }

    public static boolean isValidAmount(BigDecimal amount) {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }
}
