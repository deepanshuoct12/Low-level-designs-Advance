package org.project.demo;

import org.project.enums.BuisnessType;
import org.project.enums.TransactionType;
import org.project.model.Account;
import org.project.model.Merchant;
import org.project.model.PaymentIntent;
import org.project.model.Transaction;
import org.project.model.User;
import org.project.service.AccountService;
import org.project.service.MerchantService;
import org.project.service.PaymentServiceImpl;
import org.project.service.UserService;

import java.math.BigDecimal;

public class Driver {

    private final UserService userService = new UserService();
    private final MerchantService merchantService = new MerchantService();
    private final AccountService accountService = new AccountService();
    private final PaymentServiceImpl paymentService = PaymentServiceImpl.getInstance();

    public void runDemo() {
        // 1. Onboard a user and a merchant
        User user = new User();
        user.setName("Deepanshu");
        user.setEmail("deepanshu@example.com");
        user.setPhone("9999999999");
        user = userService.create(user);

        Merchant merchant = new Merchant();
        merchant.setName("Local Grocery Store");
        merchant.setBuisnessType(BuisnessType.RETAIL);
        merchant = merchantService.create(merchant);

        // 2. Give both of them accounts with sample balances
        Account userAccount = new Account();
        userAccount.setUserId(user.getId());
        userAccount.setBalance(new BigDecimal("500.00"));
        userAccount.setStatus("ACTIVE");
        userAccount = accountService.create(userAccount);

        Account merchantAccount = new Account();
        merchantAccount.setMerchantId(merchant.getId());
        merchantAccount.setBalance(new BigDecimal("1000.00"));
        merchantAccount.setStatus("ACTIVE");
        merchantAccount = accountService.create(merchantAccount);

        System.out.println("User account balance before payment: " + userAccount.getBalance());
        System.out.println("Merchant account balance before payment: " + merchantAccount.getBalance());

        // 3. Merchant creates a PaymentIntent for the user to pay
        PaymentIntent paymentIntent = new PaymentIntent();
        paymentIntent.setUserId(user.getId());
        paymentIntent.setMerchantId(merchant.getId());
        paymentIntent.setAmount(new BigDecimal("150.00"));
        paymentIntent.setPaymentMethodType(TransactionType.UPI);
        paymentIntent = paymentService.createPaymentIntent(paymentIntent);

        System.out.println("PaymentIntent created with status: " + paymentIntent.getStatus());

        // 4. User completes the payment
        Transaction transaction = paymentService.doTransaction(paymentIntent.getId());

        System.out.println("Transaction completed with status: " + transaction.getStatus());
        System.out.println("PaymentIntent final status: " + paymentService.getPaymentIntentStatus(paymentIntent.getId()));

        Account updatedUserAccount = accountService.getById(userAccount.getId());
        Account updatedMerchantAccount = accountService.getById(merchantAccount.getId());
        System.out.println("User account balance after payment: " + updatedUserAccount.getBalance());
        System.out.println("Merchant account balance after payment: " + updatedMerchantAccount.getBalance());
    }
}
