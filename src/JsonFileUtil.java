import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class JsonFileUtil {

    public JsonFileUtil(){
        //constructor
    }
    /*
     * function to get info corresponding to a user with a given account number from a json file
     */
    public String getJsonMember(String filename, String searchMember, String searchMemberVal, String desiredMember){
        JsonArray accntsJsonArr;

        try{
            FileReader reader = new FileReader(filename);
            accntsJsonArr = JsonParser.parseReader(reader).getAsJsonArray();
            JsonObject accountObj;
            for (JsonElement element : accntsJsonArr) {
                accountObj = element.getAsJsonObject();
                String curSearchMember= accountObj.get(searchMember).getAsString(); //get the member you are searching by from the account
                if (curSearchMember.equals(searchMemberVal)){                       //check to see if our member's value matches what we want
                    return accountObj.get(desiredMember).getAsString();             //return the desired member's value from the json object
                }
            }
        }catch(Exception e){
            System.out.println("Error in JsonFileUtil -> getJsonMember()");
            return ""; //ran into an error so just say it found nothing.
        }
        return "";  //couldn't find the account or ran into an error so return nothing
    }


    /*
     * function to set a string member of a Json object with a given account number in an array
     * returns 0 if successful, -1 if account didn't exist, -2 if other error occured
     * //TODO: handle other errors?
     */
    public int setJsonMemberString(String filename, String searchMemberName, String searchMemberVal, String newMemberName, String newVal){
        //parse through the accounts json file to find the correct account
        JsonArray accntsJsonArr;
        boolean accountFound = false; //variable to tell if the account exists
        try{
            FileReader reader = new FileReader(filename);
            accntsJsonArr = JsonParser.parseReader(reader).getAsJsonArray();

            //Check to find the correct account
            JsonObject accountObj;
            for (JsonElement element : accntsJsonArr) {
                accountObj = element.getAsJsonObject();
                String curAccountMember = accountObj.get(searchMemberName).getAsString(); //get the account number from the account
                if (curAccountMember.equals(searchMemberVal)) {
                    accountObj.addProperty(newMemberName, newVal);  //update the member value in the Json object
                    accountFound = true;
                }
            }

            //check to see if the account was found
            if(!accountFound){
                return -1; //failed to find the account
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
            System.out.println("Error in JsonFileUtil -> setJsonMemberString()");
            return -2;
        }
        return 0; //successful
    }

    /*
     * function to set an integer member of a Json object with a given member value in an array
     * returns 0 if successful, -1 if account didn't exist, -2 if error occured
     * //TODO: handle other errors?
     */
    public int setJsonMemberInt(String filename, String searchMemberName, String searchMemberVal, String newMemberName, int newVal){
        //parse through the accounts json file to find the correct account
        JsonArray accntsJsonArr;
        boolean accountFound = false; //variable to tell if the account exists
        try{
            FileReader reader = new FileReader(filename);
            accntsJsonArr = JsonParser.parseReader(reader).getAsJsonArray();

            //Check to find the correct account
            JsonObject accountObj;
            for (JsonElement element : accntsJsonArr) {
                accountObj = element.getAsJsonObject();
                String curSearchMemberVal = accountObj.get(searchMemberName).getAsString(); //get the value of the searchMember from the account
                if (curSearchMemberVal.equals(searchMemberVal)) {
                    accountObj.addProperty(newMemberName, newVal);  //update the member value in the Json object
                    accountFound = true;
                }
            }

            //check to see if the account was found
            if(!accountFound){
                return -1; //failed to find the account
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
            System.out.println("Error in JsonFileUtil -> setJsonMemberInt()");
            return -2;
        }
        return 0; //successful
    }

    /*
     * function to to check if a certain member has a certain value in the array of json objects in a file
     * returns true if exists, false if not
     * //TODO: handle other errors?
     */
    public boolean jsonContainsMemberVal(String filename, String memberName, String keyVal){
        //parse through the accounts json file to find the correct account
        JsonArray accntsJsonArr;
        try{
            FileReader reader = new FileReader(filename);
            accntsJsonArr = JsonParser.parseReader(reader).getAsJsonArray();

            //Go through all objects and try to find an object where the given member val matches the key val
            JsonObject accountObj;
            for (JsonElement element : accntsJsonArr) {
                accountObj = element.getAsJsonObject();
                String curMember = accountObj.get(memberName).getAsString(); //get the account number from the account
                if (curMember.equals(keyVal)) {
                    return true;
                }
            }
        }catch(Exception e){
            System.out.println("Error in JsonFileUtil -> jsonContainsMemberVal()");
            return false; //there was an error so return false
        }
        return false; //didn't find the member matching the key value
    }

    public int addToJsonFile(String filename, JsonObject newObject){
        JsonArray accntsJsonArr;
        try(FileReader reader = new FileReader(filename)){ //TODO: handle a fileNotFoundException (maybe just print to screen "please create account file" in the catch block so it looks like we handled it)
            try {
                accntsJsonArr = JsonParser.parseReader(reader).getAsJsonArray();
            }catch(IllegalStateException e2){
                //if the file doesn't have a json array in it, create a blank one
                accntsJsonArr = new JsonArray();  //just create a new array to append stuff to
            }

            //add the new userInfo to the array of json objects
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            accntsJsonArr.add(newObject); //add the new object to the array
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
    private final Gson gson = new GsonBuilder().create();

        public boolean deleteAccountFromJsonFile(String filename, int accountNumber) {
            // Define the correct type for the list of accounts
            Type accountListType = new TypeToken<ArrayList<BankAccount>>(){}.getType();

            try {
                // Read the existing accounts from the file
                FileReader reader = new FileReader(filename);
                List<BankAccount> accounts = gson.fromJson(reader, accountListType);
                reader.close();

                // Check if the account exists and remove it
                boolean removed = accounts.removeIf(account -> account.getAccountNumber() == accountNumber);

                if (!removed) {
                    System.out.println("Account not found for account number: " + accountNumber);
                    return false;
                }

                // Write the updated accounts back to the file
                FileWriter writer = new FileWriter(filename);
                gson.toJson(accounts, writer);
                writer.close();

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

    private List<Map<String, Object>> readAccountsFromFile(String filename) {
        try (Reader reader = new FileReader(filename)) {
            return new Gson().fromJson(reader, new TypeToken<List<Map<String, Object>>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private boolean writeAccountsToFile(String filename, List<Map<String, Object>> accounts) {
        try (Writer writer = new FileWriter(filename)) {
            new Gson().toJson(accounts, writer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
