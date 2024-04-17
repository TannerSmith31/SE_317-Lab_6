import com.google.gson.*;

import java.io.FileWriter;
import java.util.Scanner;
import java.util.Random;

import java.io.FileReader;
import java.io.IOException;

public class UtilCompany {
    //instance variables
    private String UTILITY_FILENAME = "utilityFile.json"; //TODO: maybe use a json file

    public UtilCompany(){
        //constructor
    }

    /*
     * Function to create a new user account for the utility company / bank.
     * Takes a username and a password for the new account and stores them in the utility file
     */
    public void createUserAccount(){
        boolean successful = false;
        Scanner usrIn = new Scanner(System.in); //scanner to take user input
        Random random = new Random();
        UserInfo newUsrAccnt = new UserInfo(0, null, null); //UserInfo object to store the new user account info

        //Read the file where account info is stored
        JsonArray accntsJsonArr;
        try(FileReader reader = new FileReader(UTILITY_FILENAME)){
            accntsJsonArr = JsonParser.parseReader(reader).getAsJsonArray();

            //get username from user
            while(!successful){
                System.out.print("Enter a username: ");
                String username = usrIn.nextLine();

                //check if username already exists
                if(accountMemberExists(accntsJsonArr, "username", username)){
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

                //TODO: check any restrictions for password (if any. if not, delete this todo)
                newUsrAccnt.setPassword(password); //set password into newUsrAccnt object
                successful = true;
            }

            //generate a random 6-digit account number
            successful = false;
            int accountNumber = -1;
            while(!successful){
                accountNumber = random.nextInt(999999); //generate a random 6 digit number for the new account

                //make sure the account number doesn't already exist
                if(!accountMemberExists(accntsJsonArr, "accountNumber", Integer.toString(accountNumber))){
                    newUsrAccnt.setAccountNumber(accountNumber);
                    successful = true; //if the account number doesn't exist exit the loop
                }
            }

            //add the new user account info to the json array
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            String accntJsonString = gson.toJson(newUsrAccnt);
            accntsJsonArr.add(createUserInfoJsonObject(newUsrAccnt));
            String accntsJsonString = gson.toJson(accntsJsonArr);

            //put the updated account array into the utility file
            try (FileWriter writer = new FileWriter(UTILITY_FILENAME)) {
                writer.append(accntsJsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Successfully added user. Your account number is " + accountNumber);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /*
     * Takes a username and password and checks the utility File to see if the data matches
     * returns 0 if info matches, 1 if username exists but password doesn't match, 2 if username doesn't exist, 3 otherwise
     */
    public void userLogin(String username, String password){
        JsonArray accntsJsonArr;

        try(FileReader reader = new FileReader(UTILITY_FILENAME)) {
            accntsJsonArr = JsonParser.parseReader(reader).getAsJsonArray();
        }catch(IOException e){
            e.printStackTrace();
        }
        //TODO: implement function
        //open utility file
        //see if the username exists
        //if not return 3
        //else see if the password matches the username
        //if not return 2
        //otherwise if password matches username return 0 (success)
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
