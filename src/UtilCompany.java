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
     * //TODO: make this also initialize bills when that functionality is added
     */
    public void createUserAccount(){
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
        //TODO: implement function
        //open utility file
        //read the bill payment history stored and display it to the user
    }

    /*
     * function to check a users next bill payment
     */
    public void checkNextBillPayment(int accountNumber){
        //TODO: implement function
        //open up the utility file
        //read what the next bill payment is
    }

    /*
     * makes a bill payment from the users checking account
     * TODO: see if you have to pay the bill all at once or if there is an option to pay part of it
     */
    public void makeBillPayment(int accountNumber, int amount){
        //TODO: implement function
        //get the next bill that the user has to pay
        //try to pay the amount to it
        //if the amount is too much, send message to user?
        //if not enough money in account, throw error (the transaction function will do this and maybe I catch it here and display to user?
        //if bill gets fully paid, generate a new next bill payment? or are all of them generated?
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
        return jsonObject;
    }
}
