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
    private int DAILY_C_WITHDRAW_LIMIT = 500;
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
            System.out.println("Balance check failed: account doesn't exist");
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
     * Function to withdraw money from either checking or savings
     * RULES:
     *  neither account balance can go negative
     *  Cannot withdraw from Savings account
     *  Can only withdraw up to $500 from Checking in one day
     * Return 0 if successful, return -1 for any error
     */
    public int withdraw(int accountNumber, int accountType, int amount) {
        int oldBalance;
        int newBalance;

        //see if the account exists
        if(!jsonFileUtil.jsonContainsMemberVal(FILENAME, "accountNumber", Integer.toString(accountNumber))){
            System.out.println("Withdraw unsuccessful: account doesn't exist");
            return -1; //account wasn't found
        }

        if(accountType == SAVINGSID){
            System.out.println("Withdraw unsuccessful: Not allowed to withdraw from savings");
            return -1;
        }else if(accountType == CHECKINGID){
            //Check to see if you will go over your withdraw limit
            int newDailyCheckingWithdraw = Integer.parseInt(jsonFileUtil.getJsonMember(FILENAME, "accountNumber", Integer.toString(accountNumber), "dailyCheckingWithdraw")) + amount;
            if(newDailyCheckingWithdraw > DAILY_C_WITHDRAW_LIMIT){
                System.out.println("Withdraw unsuccessful: withdraw exceeds daily limit");
                return -1;
            }

            oldBalance = Integer.parseInt(jsonFileUtil.getJsonMember(FILENAME, "accountNumber", Integer.toString(accountNumber), "checkingBalance"));
            newBalance = oldBalance - amount;

            //check if you will go negative
            if(newBalance < 0){
                System.out.println("Withdraw unsuccessful: Not enough money");
                return -1;
            }

            //carry out withdraw (update account balance and dailyAmmnt)
            jsonFileUtil.setJsonMemberInt(FILENAME, "accountNumber", Integer.toString(accountNumber), "dailyCheckingWithdraw", newDailyCheckingWithdraw);
            jsonFileUtil.setJsonMemberInt(FILENAME, "accountNumber", Integer.toString(accountNumber), "checkingBalance", newBalance);
            System.out.println("Withdraw Successful");
            return 0;

        }else{
            System.out.println("Invalid accountType in Bank -> withdraw()");
            return -1;
        }
    }

    /*
     * function to make a deposit into either checking or savings account (denoted by accountType)
     * Rules:
     *  Can only deposit $5000 a day in either Savings or checking
     * Returns 0 if successful, -1 otherwise
     */
    public int deposit(int accountNumber, int accountType, int amount) {
        int oldBalance;
        int newBalance;
        String dailyDepositName;        //The name of which daily counter we want to update (savings or checking)
        int dailyDepositLimit;          //The limit for the correct account (savings or checking)
        String accountBalanceName;      //name of the account the deposit is being done on (savings or checking)

        //see if the account exists
        if(!jsonFileUtil.jsonContainsMemberVal(FILENAME, "accountNumber", Integer.toString(accountNumber))){
            System.out.println("Deposit unsuccessful: account doesn't exist");
            return -1; //account wasn't found
        }

        if(accountType == SAVINGSID){
            dailyDepositName = "dailySavingsDeposit";
            dailyDepositLimit = DAILY_S_DEPOSIT_LIMIT;
            accountBalanceName = "savingsBalance";
        }else if(accountType == CHECKINGID){
            dailyDepositName = "dailyCheckingDeposit";
            dailyDepositLimit = DAILY_C_DEPOSIT_LIMIT;
            accountBalanceName = "checkingBalance";
        }else{
            System.out.println("Invalid accountType in Bank -> deposit()");
            return -1;
        }

        int newDailyDeposit = Integer.parseInt(jsonFileUtil.getJsonMember(FILENAME, "accountNumber", Integer.toString(accountNumber), dailyDepositName)) + amount;
        if(newDailyDeposit > dailyDepositLimit){
            System.out.println("Deposit unsuccessful: deposit exceeds daily limit");
            return -1;
        }
        oldBalance = Integer.parseInt(jsonFileUtil.getJsonMember(FILENAME, "accountNumber", Integer.toString(accountNumber), accountBalanceName));
        newBalance = oldBalance + amount;

        //carry out deposit (update balance and daily deposit)
        jsonFileUtil.setJsonMemberInt(FILENAME, "accountNumber", Integer.toString(accountNumber), accountBalanceName, newBalance);
        jsonFileUtil.setJsonMemberInt(FILENAME, "accountNumber", Integer.toString(accountNumber), dailyDepositName, newDailyDeposit);
        System.out.println("Deposit successful");
        return 0;
    }

    /*
     * function to make a transaction between accounts
     * RULES:
     *  can only transfer $100 a day from savings to checking
     *  cant make any account go negative
     * returns 0 if successful and -1 otherwise
     */
    public int transfer(int accountNumber, int fromAccountType, int amount){
        int oldToBalance;
        int newToBalance;
        int oldFromBalance;
        int newFromBalance;
        String toBalanceName;      //name of the account the deposit is being done on (savings or checking)
        String fromBalanceName;
        int newDailySavingsTransfer = 0;

        //see if the account exists
        if(!jsonFileUtil.jsonContainsMemberVal(FILENAME, "accountNumber", Integer.toString(accountNumber))){
            System.out.println("Transfer unsuccessful: account doesn't exist");
            return -1; //account wasn't found
        }

        if(fromAccountType == SAVINGSID){
            toBalanceName = "checkingBalance";
            fromBalanceName = "savingsBalance";

            //Savings account is the only one with daily transfer limits so just check that here
            newDailySavingsTransfer = Integer.parseInt(jsonFileUtil.getJsonMember(FILENAME, "accountNumber", Integer.toString(accountNumber), "dailySavingsTransfer")) + amount;
            if (newDailySavingsTransfer > DAILY_S_TRANSFER_LIMIT){
                System.out.println("Transfer Unsucessful: transaction exceeds daily transaction limit");
                return -1;
            }
        }else if(fromAccountType == CHECKINGID){
            toBalanceName = "savingsBalance";
            fromBalanceName = "checkingBalance";
        }else{
            System.out.println("Invalid accountType in Bank -> transfer()");
            return -1;
        }

        //calculate new balances for the 2 accounts
        oldFromBalance = Integer.parseInt(jsonFileUtil.getJsonMember(FILENAME, "accountNumber", Integer.toString(accountNumber), fromBalanceName));
        newFromBalance = oldFromBalance - amount;
        oldToBalance = Integer.parseInt(jsonFileUtil.getJsonMember(FILENAME, "accountNumber", Integer.toString(accountNumber), toBalanceName));
        newToBalance = oldToBalance + amount;

        //check to see if the from account has enough money to support the transaction
        if(newFromBalance < 0){
            System.out.println("Transfer Unsuccessful: not enough funds");
            return -1;
        }

        //carry out deposit (update balance and daily deposit)
        if(fromAccountType == SAVINGSID){ //only update the dailySavingsTransaction variable if you are taking money from savings and going to checking
            jsonFileUtil.setJsonMemberInt(FILENAME, "accountNumber", Integer.toString(accountNumber), "dailySavingsTransfer", newDailySavingsTransfer);
        }
        jsonFileUtil.setJsonMemberInt(FILENAME, "accountNumber", Integer.toString(accountNumber), toBalanceName, newToBalance);
        jsonFileUtil.setJsonMemberInt(FILENAME, "accountNumber", Integer.toString(accountNumber), fromBalanceName, newFromBalance);
        System.out.println("Deposit successful");

        return 0;
    }

    /*
     * resets all values daily values to 0 for the dawn of a new day
     * returns 0 if succesful and -1 if unsuccessful
     */
    public int resetDailyValues(String filename){
        //parse through the accounts json file to find the correct account
        JsonArray accntsJsonArr;
        try{
            FileReader reader = new FileReader(filename);
            accntsJsonArr = JsonParser.parseReader(reader).getAsJsonArray();

            //Check to find the correct account
            JsonObject accountObj;
            for (JsonElement element : accntsJsonArr) {
                accountObj = element.getAsJsonObject();

                //update the daily values
                accountObj.addProperty("dailyCheckingDeposit", 0);
                accountObj.addProperty("dailySavingsDeposit", 0);
                accountObj.addProperty("dailyCheckingWithdraw", 0);
                accountObj.addProperty("dailySavingsTransfer", 0);
            }

            //remake the list of accounts to put back in the given file
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String accntsJsonString = gson.toJson(accntsJsonArr);

            //put the updated account array into the given file (savings or checking)
            try (FileWriter writer = new FileWriter(filename)) {
                writer.append(accntsJsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch(Exception e){
            System.out.println("Error in Bank -> resetDailyValues()");
            return -1;
        }
        return 0; //successful
    }

    /**
     * Deletes a bank account with the given account number.
     *
     * @param accountNumber The account number of the account to be deleted.
     * @return true if the account was successfully deleted; false otherwise.
     */
    public boolean deleteAccount(int accountNumber) {
        // Check if the account exists
        if (!jsonFileUtil.jsonContainsMemberVal(FILENAME, "accountNumber", Integer.toString(accountNumber))) {
            System.out.println("Account number " + accountNumber + " does not exist.");
            return false; // Account does not exist
        }

        // If the account exists, delete the account
        boolean isDeleted = jsonFileUtil.deleteAccountFromJsonFile(FILENAME, accountNumber);

        if (isDeleted) {
            System.out.println("Account number " + accountNumber + " has been successfully deleted.");
        } else {
            System.out.println("There was an error deleting account number " + accountNumber + ".");
        }
        return isDeleted;
    }
    public String getFILENAME() {
        return FILENAME;
    }

    public void setFILENAME(String newFilename){
        this.FILENAME = newFilename;
    }

    public int getCHECKINGID() {
        return CHECKINGID;
    }

    public int getSAVINGSID(){
        return SAVINGSID;
    }

}
