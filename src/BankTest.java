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
    private String FILENAME = "bankAccounts.json";
    private final int INVALID_ACCOUNT_NUMBER = 999999; // Use an account number known to be invalid
    private final int SAVINGS = 1; // Assuming 1 represents savings account type
    private final int CHECKING = 2; // Assuming 2 represents checking account type
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
        bank.withdrawl(FILENAME,ACCOUNT_WITH_FUNDS, bank.getCHECKINGID(), withdrawalAmount);
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
        bank.withdrawl(FILENAME, ACCOUNT_WITH_FUNDS, bank.getCHECKINGID(), withdrawalAmount);
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
        bank.withdrawl(FILENAME,ACCOUNT_WITH_FUNDS, bank.getCHECKINGID(), withdrawalAmount);
        assertEquals("Balance should not change when withdrawing over the daily limit",
                initialBalance,
                bank.getBalance(ACCOUNT_WITH_FUNDS, bank.getCHECKINGID()));
    }


}