import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.*;
import static org.junit.Assert.*;
import static org.junit.Assert.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;

class BankTest {
    private Bank bank;
    private final int VALID_ACCOUNT_NUMBER = 131547; // Use an account number known to be valid
    private final int ACCOUNT_WITH_FUNDS = 132772;
    private final int INVALID_ACCOUNT_NUMBER = 999999; // Use an account number known to be invalid
    private final int INVALID_ACCOUNT_TYPE = 3; // Assuming 3 is an invalid account type

    //creating an account, with success
    @Test
    public void testOpenNewAccountSuccess() {
        bank = new Bank();
        int newAccountNumber = 123456; // Assume this number does not exist in the JSON
        assertEquals("Opening a new account should succeed",0, bank.openAccount(newAccountNumber));
        bank.deleteAccount(newAccountNumber);
    }
    //testing when an account already exists
    @Test
    public void testOpenAccountFailureAccountExists() {
        bank = new Bank();
        bank.openAccount(123456);
        int existingAccountNumber = 123456; // Assume this number already exists
        bank.openAccount(existingAccountNumber); // First, open the account to ensure it exists
        assertEquals("Opening an account that already exists should fail", -1, bank.openAccount(existingAccountNumber));
        bank.deleteAccount(existingAccountNumber);
    }

    @Test
    public void testGetBalanceWithValidSavingsAccount() {
        // Assuming this account has a savings account set up with a known balance
        bank = new Bank();
        int expectedBalance = 0; // Replace with the known balance
        assertEquals("Balance should match the expected value for savings account", expectedBalance, bank.getBalance(VALID_ACCOUNT_NUMBER, bank.getSAVINGSID()));
    }

    @Test
    public void testGetBalanceWithValidCheckingAccount() {
        // Assuming this account has a checking account set up with a known balance
        bank = new Bank();
        int expectedBalance = 0; // Replace with the known balance
        assertEquals("Balance should match the expected value for checking account", expectedBalance, bank.getBalance(VALID_ACCOUNT_NUMBER, bank.getCHECKINGID()));
    }

    @Test
    public void testGetBalanceWithInvalidAccountNumber() {
        bank = new Bank();
        int expectedFailure = -1; // Assuming that -1 indicates failure due to invalid account number
        assertEquals("Should fail due to invalid account number", expectedFailure, bank.getBalance(INVALID_ACCOUNT_NUMBER, bank.getSAVINGSID()));
    }

    @Test
    public void testGetBalanceWithInvalidAccountType() {
        bank = new Bank();
        int expectedFailure = -2; // Assuming that -2 indicates failure due to invalid account type
        assertEquals("Should fail due to invalid account type", expectedFailure, bank.getBalance(VALID_ACCOUNT_NUMBER, INVALID_ACCOUNT_TYPE));
    }
    @Test
    public void testWithdrawalWithSufficientFunds() {
        // Assuming the account has enough funds for the withdrawal
        bank = new Bank();
        int withdrawalAmount = 50;
        int initialBalance = bank.getBalance(ACCOUNT_WITH_FUNDS, bank.getCHECKINGID());
        bank.withdrawl(bank.getFILENAME(),ACCOUNT_WITH_FUNDS, bank.getCHECKINGID(), withdrawalAmount);
        assertEquals("Balance should be reduced by withdrawal amount",
                initialBalance - withdrawalAmount,
                bank.getBalance(ACCOUNT_WITH_FUNDS, bank.getCHECKINGID()));
    }

    @Test
    public void testWithdrawalWithInsufficientFunds() {
        bank = new Bank();
        // Assuming the account does not have enough funds for the withdrawal
        int withdrawalAmount = 10000; // More than the current balance
        int initialBalance = bank.getBalance(ACCOUNT_WITH_FUNDS, bank.getCHECKINGID());
        bank.withdrawl(bank.getFILENAME(), ACCOUNT_WITH_FUNDS, bank.getCHECKINGID(), withdrawalAmount);
        assertEquals("Balance should not change when withdrawing more than available funds",
                initialBalance,
                bank.getBalance(ACCOUNT_WITH_FUNDS, bank.getCHECKINGID()));
    }

    @Test
    public void testWithdrawalOverDailyLimit() {
        bank = new Bank();
        // Assuming there is a daily withdrawal limit (e.g., $500)
        int withdrawalAmount = 501; // More than the daily limit
        int initialBalance = bank.getBalance(ACCOUNT_WITH_FUNDS, bank.getCHECKINGID());
        bank.withdrawl(bank.getFILENAME(),ACCOUNT_WITH_FUNDS, bank.getCHECKINGID(), withdrawalAmount);
        assertEquals("Balance should not change when withdrawing over the daily limit",
                initialBalance,
                bank.getBalance(ACCOUNT_WITH_FUNDS, bank.getCHECKINGID()));
    }
    @Test
    public void testDepositToCheckingAccount() {
        bank = new Bank();
        int depositAmount = 500;
        int initialBalance = bank.getBalance(VALID_ACCOUNT_NUMBER, bank.getCHECKINGID());
        bank.deposit(bank.getFILENAME(), VALID_ACCOUNT_NUMBER, bank.getCHECKINGID(), depositAmount);
        assertEquals("Balance should increase by the deposit amount",
                initialBalance + depositAmount,
                bank.getBalance(VALID_ACCOUNT_NUMBER, bank.getCHECKINGID()));
    }

    @Test
    public void testDepositToSavingsAccount() {
        bank = new Bank();
        int depositAmount = 500;
        int initialBalance = bank.getBalance(VALID_ACCOUNT_NUMBER, bank.getSAVINGSID());
        bank.deposit(bank.getFILENAME(), VALID_ACCOUNT_NUMBER, bank.getSAVINGSID(), depositAmount);
        assertEquals("Balance should increase by the deposit amount",
                initialBalance + depositAmount,
                bank.getBalance(VALID_ACCOUNT_NUMBER, bank.getSAVINGSID()));
    }

    @Test
    public void testDepositOverDailyLimit() {
        bank = new Bank();
        // Assuming there is a daily deposit limit (e.g., $5000)
        int depositAmount = 5001; // More than the daily limit
        int initialBalance = bank.getBalance(VALID_ACCOUNT_NUMBER, bank.getCHECKINGID());
        bank.deposit(bank.getFILENAME(), VALID_ACCOUNT_NUMBER, bank.getCHECKINGID(), depositAmount);
        assertEquals("Balance should not change when depositing over the daily limit",
                initialBalance,
                bank.getBalance(VALID_ACCOUNT_NUMBER, bank.getCHECKINGID()));
    }

    @Test
    public void testSingleTransaction() {
        bank = new Bank();
        // Test a single transaction, e.g., deposit to checking
        int depositAmount = 100;
        int initialBalance = bank.getBalance(VALID_ACCOUNT_NUMBER, bank.getCHECKINGID());
        bank.deposit(bank.getFILENAME(), VALID_ACCOUNT_NUMBER, bank.getCHECKINGID(), depositAmount);
        assertEquals("Balance should be correctly updated after deposit",
                initialBalance + depositAmount,
                bank.getBalance(VALID_ACCOUNT_NUMBER, bank.getCHECKINGID()));
    }

    @Test
    public void testMultipleTransactions() {
        bank = new Bank();
        // Test a series of transactions: deposit then withdraw from checking
        int depositAmount = 200;
        int withdrawalAmount = 50;
        int initialBalance = bank.getBalance(VALID_ACCOUNT_NUMBER, bank.getCHECKINGID());

        // Perform a deposit
        bank.deposit(bank.getFILENAME(), VALID_ACCOUNT_NUMBER, bank.getCHECKINGID(), depositAmount);
        assertEquals("Balance should be correctly updated after deposit",
                initialBalance + depositAmount,
                bank.getBalance(VALID_ACCOUNT_NUMBER, bank.getCHECKINGID()));

        // Perform a withdrawal
        bank.withdrawl(bank.getFILENAME(), VALID_ACCOUNT_NUMBER, bank.getCHECKINGID(), withdrawalAmount);
        assertEquals("Balance should be correctly updated after withdrawal",
                initialBalance + depositAmount - withdrawalAmount,
                bank.getBalance(VALID_ACCOUNT_NUMBER, bank.getCHECKINGID()));
    }

    @Test
    public void testTransactionWithInsufficientFunds() {
        bank = new Bank();
        // Test withdrawal that exceeds the current balance
        int withdrawalAmount = 500; // Set this to more than the current balance
        int initialBalance = bank.getBalance(VALID_ACCOUNT_NUMBER, bank.getCHECKINGID());

        bank.withdrawl(bank.getFILENAME(), VALID_ACCOUNT_NUMBER, bank.getCHECKINGID(), withdrawalAmount);
        assertEquals("Balance should not change when withdrawal exceeds balance",
                initialBalance,
                bank.getBalance(VALID_ACCOUNT_NUMBER, bank.getCHECKINGID()));
    }
    @Test
    public void testTransactionFromSavingsToCheckingSuccess() {
        bank = new Bank();
        // Perform a transfer from savings to checking that should succeed
        int transferAmount = 10; // An amount within the daily limit
        int initialCheckingBalance = bank.getBalance(ACCOUNT_WITH_FUNDS, bank.getCHECKINGID());
        int initialSavingsBalance = bank.getBalance(ACCOUNT_WITH_FUNDS, bank.getSAVINGSID());
        int result = bank.transaction(ACCOUNT_WITH_FUNDS, bank.getSAVINGSID(), transferAmount);

        // Verify that the transaction was successful
        assertEquals("Transaction should succeed", 0, result);
        // Verify that the checking balance has increased by the transfer amount
        assertEquals("Checking balance should be increased",
                initialCheckingBalance + transferAmount,
                bank.getBalance(ACCOUNT_WITH_FUNDS, bank.getCHECKINGID()));
        // Verify that the savings balance has decreased by the transfer amount
        assertEquals("Savings balance should be decreased",
                initialSavingsBalance - transferAmount,
                bank.getBalance(ACCOUNT_WITH_FUNDS, bank.getSAVINGSID()));
    }

    @Test
    public void testTransactionFromSavingsToCheckingDailyLimitExceeded() {
        bank = new Bank();
        // Attempt to transfer an amount that exceeds the daily transfer limit
        int transferAmount = bank.getDAILY_S_TRANSFER_LIMIT() + 1; // Exceeding the limit
        int result = bank.transaction(VALID_ACCOUNT_NUMBER, bank.getSAVINGSID(), transferAmount);

        // Verify that the transaction failed due to daily limit
        assertEquals("Transaction should fail due to daily limit", -1, result);
    }

    @Test
    public void testTransactionFromSavingsToCheckingWithInsufficientFunds() {
        bank = new Bank();
        // Attempt to transfer more than the available savings balance
        int transferAmount = bank.getBalance(VALID_ACCOUNT_NUMBER, bank.getSAVINGSID()) + 1; // More than available balance
        int result = bank.transaction(VALID_ACCOUNT_NUMBER, bank.getSAVINGSID(), transferAmount);

        // Verify that the transaction failed due to insufficient funds
        assertEquals("Transaction should fail due to insufficient funds", -1, result);
    }

    @Test
    public void testTransactionWithInvalidAccount() {
        bank = new Bank();
        // Attempt to perform a transaction with an invalid account number
        int transferAmount = 100; // Any amount for the purpose of this test
        int result = bank.transaction(INVALID_ACCOUNT_NUMBER, bank.getSAVINGSID(), transferAmount);

        // Verify that the transaction failed due to invalid account number
        assertEquals("Transaction should fail due to invalid account", -1, result);
    }
//    @Test
//    public void testResetDailyValues() {
//        private int NEW_ACCOUNT_NUMBER = 89898989;
//        bank = new Bank();
//        // Initially, we assume that the test account does not exist
//        bank.openAccount(NEW_ACCOUNT_NUMBER);
//        bank.deposit(bank.getFILENAME(), NEW_ACCOUNT_NUMBER, bank.getCHECKINGID(), 1000); // Deposit to update daily values
//
//        // Verify that daily values are updated by the deposit
//        assertTrue("Daily checking deposit should be updated", bank.getDailyCheckingDeposit(NEW_ACCOUNT_NUMBER) > 0);
//
//        // Reset daily values
//        int resetResult = bank.resetDailyValues(bank.getFILENAME());
//        assertEquals("Reset daily values should succeed", 0, resetResult);
//
//        // Check that all daily values are reset to 0
//        assertEquals("Daily checking deposit should be reset", 0, bank.getDailyCheckingDeposit(NEW_ACCOUNT_NUMBER));
//        assertEquals("Daily savings deposit should be reset", 0, bank.getDailySavingsDeposit(NEW_ACCOUNT_NUMBER));
//        assertEquals("Daily checking withdrawal should be reset", 0, bank.getDailyCheckingWithdrawal(NEW_ACCOUNT_NUMBER));
//        assertEquals("Daily savings transfer should be reset", 0, bank.getDailySavingsTransfer(NEW_ACCOUNT_NUMBER));
//    }

}