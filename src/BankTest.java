import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.*;
import static org.junit.Assert.*;
import static org.junit.Assert.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;

class BankTest {
    private Bank bank;

    //creating an account, with success
    @Test
    public void testOpenNewAccountSuccess() {
        bank = new Bank();
        bank.deleteAccount(123456);
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

}