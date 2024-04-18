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

    //Bill variables. paid bills are set up as strings in the following way: "<billID>,<billAmnt>,<dueDate>,<datePaid>". unpaid bills are set up as:"<billID>,<billAmnt>,<dueDate>,<amntPaid>".
    private String nextBill;
    private String billHistory;  //this array is structured as "<bill1>.<bill2>.<bill3>" where bills are seperated by dots


    public UserInfo(){
        //default constructor
    }

    public UserInfo(int accountNumber, String username, String password){
        this.accountNumber = accountNumber;
        this.username = username;
        this.password = password;

        //generate random bill data
        this.billHistory = "100,3,2.100,5,4.100,7,5";
        this.nextBill = "100,10,0";
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

    public String getNextBill() {
        return nextBill;
    }

    public void setNextBill(String nextBill) {
        this.nextBill = nextBill;
    }

    public String getBillHistory() {
        return billHistory;
    }

    public void setBillHistory(String billHistory) {
        this.billHistory = billHistory;
    }


    public JsonObject toJsonObject(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("accountNumber", accountNumber);
        jsonObject.addProperty("username", username);
        jsonObject.addProperty("password", password);
        jsonObject.addProperty("billHistory", billHistory);
        jsonObject.addProperty("nextBill", nextBill);
        return jsonObject;
    }
}
