import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        UtilCompany testCompany = new UtilCompany();
        testCompany.createUserAccount();
//        // Step 1: Create a Java object representing the data
//        UserInfo[] testUserInfos = {new UserInfo(222222, "username2", "password2"), new UserInfo(223333, "username2", "password2")};
//
//        // Step 2: Convert the Java object to a JSON string
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String jsonString = gson.toJson(testUserInfos);
//
//        // Step 3: Write the JSON string to a file
//        try (FileWriter writer = new FileWriter("utilityFile.json")) {
//            writer.append(jsonString);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }




//        UserInfo testInfo = new UserInfo(111111, "username1", "password1");
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String jsonString = gson.toJson(testInfo);
//        JsonObject jsonObject;
//        try(FileReader reader = new FileReader("utilityFile.json")){
//            jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
//        }catch(IOException e){
//            e.printStackTrace();
//        }

        System.out.println("Hello world!");
    }
}