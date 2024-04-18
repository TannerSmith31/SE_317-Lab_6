import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.*;
import static org.junit.Assert.*;
import static org.junit.Assert.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;

class BankTest {
    private Bank bank;

//    @Before
//    public void setUp() {
//        bank = new Bank();
//        // Assuming there is a way to clear the JSON file or mock it for testing
//        // This might involve setting up a temporary test file or using a mocking framework
//    }

    @Test
    public void testOpenNewAccountSuccess() {
        bank = new Bank();
        int newAccountNumber = 123456; // Assume this number does not exist in the JSON
        assertEquals("Opening a new account should succeed",0, bank.openAccount(newAccountNumber));
        bank.deleteAccount(newAccountNumber);
    }

    @Test
    public void testOpenAccountFailureAccountExists() {
        bank = new Bank();
        int existingAccountNumber = 123456; // Assume this number already exists
        bank.openAccount(existingAccountNumber); // First, open the account to ensure it exists
        assertEquals("Opening an account that already exists should fail", -1, bank.openAccount(existingAccountNumber));
    }

//    @Test
//    public void testOpenAccountFailureWriteError() {
//        int validAccountNumber = 789012; // Assume this is a valid new number
//        // Here you'd need to simulate a write error, possibly by mocking jsonFileUtil's behavior
//        // For the purpose of this example, let's assume we have a method to force a write error:
//        bank.forceWriteError(true); // A method to simulate error, you will need to implement this logic
//        assertEquals("Simulating a JSON write error should return -2", -2, bank.openAccount(validAccountNumber));
//        bank.forceWriteError(false); // Reset the error simulation
//    }

    @After
    public void tearDown() {
        // Clean up the test environment if necessary
        // This might involve deleting the test JSON file or resetting mock states
    }
    @org.junit.jupiter.api.Test
    void openAccount() {
    }

    @org.junit.jupiter.api.Test
    void getBalance() {
    }

    @org.junit.jupiter.api.Test
    void withdrawl() {
    }

    @org.junit.jupiter.api.Test
    void deposit() {
    }

    @org.junit.jupiter.api.Test
    void transaction() {
    }

    @org.junit.jupiter.api.Test
    void getFILENAME() {
    }

    @org.junit.jupiter.api.Test
    void getCHECKINGID() {
    }

    @org.junit.jupiter.api.Test
    void getSAVINGSID() {
    }
}