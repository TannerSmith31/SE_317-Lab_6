import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Bank {
    //instance variables
    private String FILENAME = "userAccounts.json";
    private JsonFileUtil jsonFileUtil = new JsonFileUtil();
    private int SAVINGSID = 1;  //number to represent we want to work with the savings account
    private int CHECKINGID = 2; //number to represent we want to work with the checking account
    public Bank(){
        //constructor
    }

    /*
     * Function to get the balance in a users account based on their account number
     * Pass in the savingsFilename as the filename parameter to read from savings
     * Pass in the checkingFilename as the filename parameter to read from savings
     * Throws an error if the user doesn't have a savings account set up
     * Returns the balance of the user's account (or -1 if account not found. -2 if invalid accountType).
     */
    public int getBalance(int accountNumber, int accountType){
        //see if the account exists
        if(!jsonFileUtil.jsonContainsMemberVal(FILENAME, "accountNumber", Integer.toString(accountNumber))){
            return -1; //account wasn't found
        }
        if(accountType == SAVINGSID){
            return Integer.parseInt(jsonFileUtil.getJsonMember(FILENAME, "accountNumber", Integer.toString(accountNumber), "savingsBalance"));
        }else if(accountType == CHECKINGID){
            return Integer.parseInt(jsonFileUtil.getJsonMember(FILENAME, "accountNumber", Integer.toString(accountNumber), "checkingBalance"));
        }else{
            return -2; //the accountType wasn't SAVINGSID or CHECKINGID
        }
    }

    /*
     * Function to update the value in the savings account. Amount will be added (or subtracted if negative) to existing balance
     * Throws an error if user doesn't have a savings account
     * Throws error if will cause the account balance to go negative
     * Returns 0 if successful, -1 if not enough funds, -2 if cannot find the account
     */
    public int withdrawl(String filename, int accountNumber, int accountType, int amount) {
        //TODO: implement function
        return 0; //successful
    }

    public int deposit(String filename, int accountNumber, int accountType, int amount) {
        //TODO: implement function
        return 0; //successful
    }

    /*
     * function to make a savings account transaction
     * SAVINGS RULES:
     *  balance can't go below 0
     *  can't deposit more than $5000 in one day
     *  can't withdraw money
     *  can't transfer over $100 per day to checking
     * CHECKING RULES:
     *  balance can't go below 0
     *  can't deposit more than $5000 in one day
     *  can't withdraw more than $500 in one day
     *  can transfer any
     */
    public void transaction(int accountNumber){
        //TODO: implement function
    }

    public String getFILENAME() {
        return FILENAME;
    }

    public int getCHECKINGID() {
        return CHECKINGID;
    }

    public int getSAVINGSID(){
        return SAVINGSID;
    }
}
