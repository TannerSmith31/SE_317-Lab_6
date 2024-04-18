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

    //TODO: add an array of bill payment history here
    //TODO: add a variable for next bill payment

    public UserInfo(){
        //default constructor
    }

    public UserInfo(int accountNumber, String username, String password){
        this.accountNumber = accountNumber;
        this.username = username;
        this.password = password;

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
