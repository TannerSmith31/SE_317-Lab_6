/*
 * Object to store information about a users bank account
 * Used for creating a json object to store user info about accounts
 */
public class AccountInfo {

    int accountNumber;
    int balance;

    //AccountInfo constructor
    public AccountInfo(int accountNumber, int balance){
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public int getBalance() {
        return balance;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

}
