import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Bank {
    //instance variables
    private String SAVINGS_FILENAME = "savings.json";  //text file to store savings account information for users //TODO: maybe use JSON file
    private String CHECKING_FILENAME = "checking.json"; //text file to store checking account information for users //TODO: maybe use JSON file

    public Bank(){
        //constructor
    }

    /*
     * function to create a new savings or checking account for a user and store the info in the savings account text file
     * Pass in the savingsFilename as the filename parameter to add a savings account
     * Pass in the checkingFilename as the filename parameter to add a checking account
     * throws an error if the account you are trying to create already exists
     * Returns 0 is successful, 1 otherwise
     */
    public int openAccount(int accountNumber, String filename){
        JsonArray accntsJsonArr;
        try(FileReader reader = new FileReader(filename)){ //TODO: handle a fileNotFoundException (maybe just print to screen "please create account file" in the catch block so it looks like we handled it)
            try {
                accntsJsonArr = JsonParser.parseReader(reader).getAsJsonArray();

                //Check to see if the account already exists
                JsonObject accountObj;
                for (JsonElement element : accntsJsonArr) {
                    accountObj = element.getAsJsonObject();
                    int curAccountNumber = accountObj.get("accountNumber").getAsInt(); //get the account number from the account
                    if (curAccountNumber == accountNumber) {     //check to see if the account number
                        System.out.println("Failed to open account. Account already exists for user.");
                        return 1; //unsuccessful
                    }
                }
            }catch(IllegalStateException e2){ //TODO: maybe in main have it check if the 3 neccesary files exist and if not, create them and put an empty json array in each so I don't have to worry about this OR the file not found
                //this is thrown if the json file is empty.
                //So if the json file is empty we don't have to worry about checking whether the account exists
                accntsJsonArr = new JsonArray();  //just create a new array to append stuff to
            }

            //If user doesn't already have the bank account make it
            AccountInfo newBankAccnt = new AccountInfo(accountNumber, 0); //new accounts start with no money

            //add the new user account info to the json array
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            accntsJsonArr.add(createAccountInfoJsonObject(newBankAccnt));
            String accntsJsonString = gson.toJson(accntsJsonArr);

            //put the updated account array into the given file (savings or checking)
            try (FileWriter writer = new FileWriter(filename)) {
                writer.append(accntsJsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return 0; //succsessful
    }

    /*
     * Function to get the balance in a users account based on their account number
     * Pass in the savingsFilename as the filename parameter to read from savings
     * Pass in the checkingFilename as the filename parameter to read from savings
     * Throws an error if the user doesn't have a savings account set up
     * Returns the balance of the user's account (or -1 if account not found).
     */
    public int getAccountBalance(int accountNumber, String filename){
        JsonArray accntsJsonArr;
        try(FileReader reader = new FileReader(filename)){ //TODO: handle a fileNotFoundException (maybe just print to screen "cannot find savings file File not Found" in the catch block so it looks like we handled it
            accntsJsonArr = JsonParser.parseReader(reader).getAsJsonArray();

            //Check to find the correct account
            JsonObject accountObj;
            for (JsonElement element : accntsJsonArr) {
                accountObj = element.getAsJsonObject();
                int curAccountNumber = accountObj.get("accountNumber").getAsInt(); //get the account number from the account
                if (curAccountNumber == accountNumber) {
                    return accountObj.get("balance").getAsInt();    //return account balance if accountNumber is found
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        //TODO: print to user that account wasn't found?
        return -1; //could not find the account so return -1
    }

    /*
     * Function to update the value in the savings account. Amount will be added (or subtracted if negative) to existing balance
     * Throws an error if user doesn't have a savings account
     * Throws error if will cause the account balance to go negative
     * TODO: look at how else this can throw errors (from PDF)
     */
    public void updateSavingsBalance(int accountNumber, int ammount) {
        //TODO: implement this function
        //open savings account file
        //see if savings account exists with given number
        //if not throw an error
        //if so, add the given ammount to the balance and save it to the file
    }

    /*
     * Function to update the value in the savings account. Ammount will be added (or subtracted if negative) to existing balance
     * Throws an error if user doesn't have a savings account
     * Throws error if will cause the account balance to go negative
     * TODO: look at how else this can throw errors (from PDF)
     */
    public void updateCheckingBalance(int accountNumber, int amount){
        //TODO: implement this function
        //open checking account file
        //see if checking account exists with given number
        //if not throw an error
        //Check for other error conditions
        //if all is good, add the given amount to the balance and save it to the file
    }

    /*
     * Helper method to create a JSON object from an AccountInfo instance
     */
    private static JsonObject createAccountInfoJsonObject(AccountInfo bankAccount) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("accountNumber", bankAccount.getAccountNumber());
        jsonObject.addProperty("balance", bankAccount.getBalance());
        return jsonObject;
    }

    public String getCHECKING_FILENAME() {
        return CHECKING_FILENAME;
    }

    public String getSAVINGS_FILENAME() {
        return SAVINGS_FILENAME;
    }
}
