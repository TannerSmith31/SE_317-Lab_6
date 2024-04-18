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

    //Bill variables. bills are set up as strings in the following way: "<billID>,<billAmnt>,<dueDate>,<datePaid>"
    private String nextBill;
    private String[] billHistory = new String[3];


    public UserInfo(){
        //default constructor
    }

    public UserInfo(int accountNumber, String username, String password){
        this.accountNumber = accountNumber;
        this.username = username;
        this.password = password;

        //generate random bill history
        this.billHistory[0] = "100,3,2";
        this.billHistory[1] = "100,5,4";
        this.billHistory[2] = "100,7,5";

        this.nextBill = "100,10,-1";


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

    public JsonObject toJsonObject(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("accountNumber", accountNumber);
        jsonObject.addProperty("username", username);
        jsonObject.addProperty("password", password);
        //TODO: add bill stuff
        return jsonObject;
    }
}
