import com.google.gson.*;

import java.io.FileWriter;
import java.util.Scanner;
import java.util.Random;

import java.io.FileReader;
import java.io.IOException;

public class UtilCompany {
    //instance variables
    private String FILENAME = "userAccounts.json";
    private JsonFileUtil jsonFileUtil = new JsonFileUtil();
    private Bank bank = new Bank();

    public UtilCompany(){
        //constructor
    }

    /*
     * Function to create a new user account for the utility company / bank.
     * Takes a username and a password for the new account and stores them in the utility file
     * returns the account number of the newly created account
     */
    public int createUserAccount(){
        boolean successful = false;
        Scanner usrIn = new Scanner(System.in); //scanner to take user input
        Random random = new Random();
        UserInfo newUsrAccnt = new UserInfo(0, null, null); //UserInfo object to store the new user account info

        //get username from user
        while(!successful){
            System.out.print("Enter a username: ");
            String username = usrIn.nextLine();

            //check if username already exists
            if(jsonFileUtil.jsonContainsMemberVal(FILENAME, "username", username)){
                System.out.println("Username already Taken"); //found an account with the same username so reask
            }else{
                newUsrAccnt.setUsername(username); //set username into newUsrAccnt object
                successful = true; //username is valid so continue
            }
        }

        //create a password for this new account
        successful = false; //reset success variable for second loop
        while(!successful){
            System.out.print("Enter a password: ");
            String password = usrIn.nextLine();
            newUsrAccnt.setPassword(password); //set password into newUsrAccnt object
            successful = true;
        }

        //generate a random 6-digit account number
        successful = false;
        int accountNumber = -1;
        while(!successful){
            accountNumber = random.nextInt(899999) + 100000; //generate a random 6 digit number for the new account

            //make sure the account number doesn't already exist
            if(!jsonFileUtil.jsonContainsMemberVal(FILENAME, "accountNumber", Integer.toString(accountNumber))){
                newUsrAccnt.setAccountNumber(accountNumber);
                successful = true; //if the account number doesn't exist exit the loop
            }
        }

        //add the new user account info to the json array
        jsonFileUtil.addToJsonFile(FILENAME, newUsrAccnt.toJsonObject());

        //add an account for the user in the BankAccounts.json as well
        bank.openAccount(accountNumber);
        System.out.println("Successfully added user. Your account number is " + accountNumber);
        return accountNumber;
    }

    /*
     * Takes a (username or accountNumber) and password and checks the utility File to see if the data matches
     * identificationMemberType will be either "username" (if logging in with username) or "accountNumber" (if logging in with accountNumber) and identificationVal will be what the user enters for that field
     * returns 0 if info matches, -1 if username exists but password doesn't match, -2 if username doesn't exist, -3 otherwise
     */
    public int userLogin(String identificationMemberName, String identificationVal, String password){

        //see if the users identificationVal exists as a value under the identificationMember of one of the accounts in the array
        if(!jsonFileUtil.jsonContainsMemberVal(FILENAME, identificationMemberName, identificationVal)){
            return -2; //the username/accountNumber isn't in the file
        }

        //get the password that is stored in the file with the account the user entered
        String passwordFromFile = jsonFileUtil.getJsonMember(FILENAME, identificationMemberName, identificationVal, "password");

        //if the password the user entered didn't match what is on file, return 1
        if(!passwordFromFile.equals(password)){
            return -1;
        }

        //account exists and password matches so return 0
        return 0;
    }

    /*
     * function to check a users bill payment history
     */
    public void checkBillPaymentHistory(int accountNumber){
        String accountBillHistoryRaw = jsonFileUtil.getJsonMember(FILENAME, "accountNumber", Integer.toString(accountNumber), "billHistory");
        String[] accountBillHistoryArr = accountBillHistoryRaw.split("\\.");
        System.out.println("--Bill History--");
        for(int i = 0; i<3; i++){
            String[] singleBillArr = accountBillHistoryArr[i].split(",");
            System.out.print(i +". ammount: " + singleBillArr[0] + " dueDate: " + singleBillArr[1] + " datePaid: ");
            if(Integer.parseInt(singleBillArr[2]) <0){
                System.out.println("UNPAID");
            }else{
                System.out.println(singleBillArr[2]);
            }
        }
    }

    /*
     * function to check a users next bill payment.
     * The nextBillPayment variable is set up as "<amntDue>,<DueDate>,<AmntPaid>"
     */
    public void checkNextBill(int accountNumber){
        String nextBillRaw = jsonFileUtil.getJsonMember(FILENAME, "accountNumber", Integer.toString(accountNumber), "nextBill");
        String[] nextBillArr = nextBillRaw.split(",");
        System.out.println("Next Bill due:");
        System.out.println(">Amount Due: " + nextBillArr[0]);
        System.out.println(">Due Date: " + nextBillArr[1]);
        System.out.println(">Amount Paid: " + nextBillArr[2]);
    }

    /*
     * makes a bill payment from the users checking account
     * NOTE: you cannot overpay a bill (you have to give exact amount due or less)
     * TODO: make this update bill history and generate a new bill?
     */
    public int makeBillPayment(int accountNumber, int amount){

        //Get the next bill the user has to pay and take out the total cost and how much has been paid
        String nextBillPaymentRaw = jsonFileUtil.getJsonMember(FILENAME, "accountNumber", Integer.toString(accountNumber), "nextBill");
        String[] nextBillPaymentArr = nextBillPaymentRaw.split(",");

        int billAmount = Integer.parseInt(nextBillPaymentArr[0]);
        int amountPaid = Integer.parseInt(nextBillPaymentArr[2]);
        int remainingAmount = billAmount - amountPaid;

        //See if the user is trying to overpay
        if(amount > remainingAmount){
            System.out.println("Failed to pay bill: overpaid");
            return -1;
        }

        int usrCheckingBalance = Integer.parseInt(jsonFileUtil.getJsonMember(bank.getFILENAME(), "accountNumber", Integer.toString(accountNumber), "checkingBalance"));

        //check if user has enough money in their checking account
        if(amount > usrCheckingBalance){
            System.out.println("Failed to pay bill: not enough funds");
        }

        //you have enough money to pay the bill
        if(bank.withdraw(accountNumber, bank.getCHECKINGID(), amount) == 0){
            int newAmountPaid = amountPaid + amount;
            String newBillPaymentUpdated = nextBillPaymentArr[0] + "," + nextBillPaymentArr[1] + "," + newAmountPaid;
            jsonFileUtil.setJsonMemberString(FILENAME, "accountNumber", Integer.toString(accountNumber), "nextBill", newBillPaymentUpdated);
            System.out.println("Successfully made bill payment");
            return 0;
        }else {
            //you already met your max withdraw for the day. Withdraw function already notifies user of this
            return -1;
        }
    }

    /*
     * Function to go through an array of Json objects and see if a certain member has an existing value
     * (like seeing if an account already has a certain username)
     * Returns true if the member exists and false otherwise
     */
    public boolean accountMemberExists(JsonArray accounts, String member, String key){
        JsonObject accountObj;
        for (JsonElement element : accounts) {
            accountObj = element.getAsJsonObject();
            String curAccountMember= accountObj.get(member).getAsString(); //get the account number from the account
            if (curAccountMember.equals(key)){
                return true;
            }
        }
        return false; //went through all accounts and didn't find the number
    }

    /*
     * Helper method to create a JSON object from a UserInfo instance
     */
    private static JsonObject createUserInfoJsonObject(UserInfo userAccount) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("accountNumber", userAccount.getAccountNumber());
        jsonObject.addProperty("username", userAccount.getUsername());
        jsonObject.addProperty("password", userAccount.getPassword());
        jsonObject.addProperty("billHistory", userAccount.getBillHistory().toString());
        jsonObject.addProperty("nextBill", userAccount.getNextBill());
        return jsonObject;
    }

    public String getFILENAME() {
        return FILENAME;
    }
}
