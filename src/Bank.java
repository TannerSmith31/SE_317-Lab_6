import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Bank {
    //instance variables
    private String FILENAME = "bankAccounts.json";
    private JsonFileUtil jsonFileUtil = new JsonFileUtil();
    private int SAVINGSID = 1;  //number to represent we want to work with the savings account
    private int CHECKINGID = 2; //number to represent we want to work with the checking account
    private int DAILY_C_WITHDRAWL_LIMIT = 500;
    private int DAILY_C_DEPOSIT_LIMIT = 5000;
    private int DAILY_S_DEPOSIT_LIMIT = 5000;
    private int DAILY_S_TRANSFER_LIMIT = 100;
    public Bank(){
        //constructor
    }

    /*
     * Function to create a new bank account and add it to the bankAccoutns.json file
     * given an accountNumber that links it to a utilCompany account
     * If an account with the given accountNumber already exists, return -1. Return -2 if an error occured when adding account to file
     */
    public int openAccount(int accountNumber){
        //See if an account already exists
        if(jsonFileUtil.jsonContainsMemberVal(FILENAME, "accountNumber", Integer.toString(accountNumber))){
            System.out.println("Cannot open new bank account for " + accountNumber + ". Account already exists");
            return -1; //account with accountNumber already exists
        }
        BankAccount newAccount = new BankAccount(accountNumber);
        if(jsonFileUtil.addToJsonFile(FILENAME, newAccount.toJsonObject()) == 0) {
            return 0; //success
        }else{
            return -2; //failed
        }
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
     * Function to withdrawl money from either checking or savings
     * RULES:
     *  neither account balance can go negative
     *  Cannot withdraw from Savings account
     *  Can only withdraw up to $500 from Checking in one day
     * Return 0 if successful, return -1 for any error
     */
    public int withdrawl(String filename, int accountNumber, int accountType, int amount) {
        int oldBalance;
        int newBalance;

        if(accountType == SAVINGSID){
            System.out.println("Withdrawl unsuccessful: Not allowed to withdraw from savings");
            return -1;
        }else if(accountType == CHECKINGID){
            //Check to see if you will go over your withdrawl limit
            int newDailyCheckingWithdrawl = Integer.parseInt(jsonFileUtil.getJsonMember(FILENAME, "accountNumber", Integer.toString(accountNumber), "dailyCheckingWithdrawl")) + amount;
            if(newDailyCheckingWithdrawl > DAILY_C_WITHDRAWL_LIMIT){
                System.out.println("Withdrawl unsuccessful: withdrawl exceeds daily limit");
                return -1;
            }

            oldBalance = Integer.parseInt(jsonFileUtil.getJsonMember(FILENAME, "accountNumber", Integer.toString(accountNumber), "checkingBalance"));
            newBalance = oldBalance - amount;

            //check if you will go negative
            if(newBalance < 0){
                System.out.println("Withdrawl unsuccessful: Not enough money");
                return -1;
            }

            System.out.println("Withdraw Successful");
            return 0;

        }else{
            System.out.println("Invalid accountType in Bank -> withdrawl()");
            return -1;
        }
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
