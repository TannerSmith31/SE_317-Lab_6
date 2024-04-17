import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 * class to create an object that stores all information about a users util account info
 * Used to create a json file
 */
public class UserInfo {
    private int accountNumber;
    private String username;
    private String password;
    private int savingsBalance;
    private int checkingBalance;
    private int dailySavingsDeposit;
    private int dailyCheckingDeposit;
    private int dailySavingsTransfer;
    private int dailyCheckingWithdrawl;

    //TODO: add an array of bill payment history here
    //TODO: add a variable for next bill payment

    public UserInfo(){
        //default constructor
    }

    public UserInfo(int accountNumber, String username, String password){
        this.accountNumber = accountNumber;
        this.username = username;
        this.password = password;
        this.checkingBalance = 0;
        this.dailyCheckingDeposit = 0;
        this.savingsBalance = 0;
        this.dailySavingsDeposit = 0;
        this.dailyCheckingWithdrawl = 0;
        this.dailySavingsTransfer = 0;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber){
        this.accountNumber = accountNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public int getSavingsBalance() {
        return savingsBalance;
    }

    public void setSavingsBalance(int savingsBalance) {
        this.savingsBalance = savingsBalance;
    }

    public int getCheckingBalance() {
        return checkingBalance;
    }

    public void setCheckingBalance(int checkingBalance) {
        this.checkingBalance = checkingBalance;
    }

    public int getDailySavingsDeposit() {
        return dailySavingsDeposit;
    }

    public void setDailySavingsDeposit(int dailySavingsDeposit) {
        this.dailySavingsDeposit = dailySavingsDeposit;
    }

    public int getDailyCheckingDeposit() {
        return dailyCheckingDeposit;
    }

    public void setDailyCheckingDeposit(int dailyCheckingDeposit) {
        this.dailyCheckingDeposit = dailyCheckingDeposit;
    }

    public int getDailyCheckingWithdrawl() {
        return dailyCheckingWithdrawl;
    }

    public void setDailyCheckingWithdrawl(int dailyCheckingWithdrawl) {
        this.dailyCheckingWithdrawl = dailyCheckingWithdrawl;
    }

    public int getDailySavingsTransfer() {
        return dailySavingsTransfer;
    }

    public void setDailySavingsTransfer(int dailySavingsTransfer) {
        this.dailySavingsTransfer = dailySavingsTransfer;
    }

    public JsonObject toJsonObject(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("accountNumber", accountNumber);
        jsonObject.addProperty("username", username);
        jsonObject.addProperty("password", password);
        jsonObject.addProperty("savingsBalance", savingsBalance);
        jsonObject.addProperty("checkingBalance", checkingBalance);
        jsonObject.addProperty("dailySavingsDeposit", dailySavingsDeposit);
        jsonObject.addProperty("dailyCheckingDeposit", dailyCheckingDeposit);
        jsonObject.addProperty("dailyCheckingWithdrawl", dailyCheckingWithdrawl);
        jsonObject.addProperty("dailySavingsTransfer", dailySavingsTransfer);
        return jsonObject;
    }
}
