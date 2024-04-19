import com.google.gson.*;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.*;
import java.util.stream.StreamSupport;

class JsonFileUtilTest {
    private JsonFileUtil util;
    private final String TEST_FILE = "testData.json";
    @Test
    public void testReadJsonData() throws FileNotFoundException {
        JsonFileUtil util = new JsonFileUtil();
        FileReader reader = new FileReader("testData.json");
        JsonArray accntsJsonArr;
        accntsJsonArr = JsonParser.parseReader(reader).getAsJsonArray();
        assertNotNull("Json array should not be null", String.valueOf(accntsJsonArr));
        assertFalse(accntsJsonArr.size() == 0, "Json array should not be empty");
    }
    @Test
    public void testWriteJsonData() throws IOException {
        JsonFileUtil util = new JsonFileUtil();
        String filename = "testData.json";
        JsonArray jsonArray = new JsonArray();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("testKey", "testValue");
        jsonArray.add(jsonObject);

        FileWriter writer = new FileWriter(filename);
        writer.write(new Gson().toJson(jsonArray));
        writer.close();

        // Read back to check
        JsonArray readBackData = JsonParser.parseReader(new FileReader(filename)).getAsJsonArray();
        assertEquals("Should read back what was written", jsonArray, readBackData);
    }
    @Test
    public void testUpdateJsonData() throws IOException {
        util = new JsonFileUtil();
        // Prepare the test file with initial data
        try (Writer writer = new FileWriter(TEST_FILE)) {
            JsonArray jsonArray = new JsonArray();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("accountNumber", "123456");
            jsonObject.addProperty("balance", 100);
            jsonArray.add(jsonObject);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonArray, writer);
        }
        String searchMemberName = "accountNumber";
        String searchMemberVal = "123456";
        String newMemberName = "balance";
        int newVal = 200;

        int result = util.setJsonMemberInt(TEST_FILE, searchMemberName, searchMemberVal, newMemberName, newVal);
        assertEquals("Update should return success", 0, result);

        // Read back the file to verify the update
        try (Reader reader = new FileReader(TEST_FILE)) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            JsonObject updatedObject = jsonArray.get(0).getAsJsonObject();
            int updatedBalance = updatedObject.get(newMemberName).getAsInt();
            assertEquals("Balance should be updated in the JSON file", newVal, updatedBalance);
        } catch (IOException e) {
            fail("Failed to read the test file");
        }
    }

    @Test
    public void testJsonContainsMemberVal() throws IOException {
        JsonFileUtil util = new JsonFileUtil();
        String filename = "testData.json";

        // Assuming data is already in file
        boolean exists = util.jsonContainsMemberVal(filename, "accountNumber", "123456");
        assertTrue(exists, "Entry should exist");
    }
    @Test
    public void testDeleteAccountFromJsonFile() {
            util = new JsonFileUtil();
            // Setup a test JSON file with predefined accounts
            try (Writer writer = new FileWriter(TEST_FILE)) {
                JsonArray jsonArray = new JsonArray();
                JsonObject jsonObject1 = new JsonObject();
                jsonObject1.addProperty("accountNumber", 123456);
                jsonArray.add(jsonObject1);
                JsonObject jsonObject2 = new JsonObject();
                jsonObject2.addProperty("accountNumber", 789101);
                jsonArray.add(jsonObject2);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(jsonArray, writer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Delete one of the accounts
            assertTrue(util.deleteAccountFromJsonFile(TEST_FILE, 123456), "Account should be successfully deleted");

            // Verify that the account is no longer in the file
            try (Reader reader = new FileReader(TEST_FILE)) {
                JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
                // Convert JsonArray to Stream for processing
                boolean accountExists = StreamSupport.stream(jsonArray.spliterator(), false)
                        .map(JsonElement::getAsJsonObject)
                        .anyMatch(account -> account.get("accountNumber").getAsInt() == 123456);
                assertFalse(accountExists, "Deleted account should not be found");
            } catch (IOException e) {
                fail("Failed to read the test file");
            }

    }


    @Test
    public void testDeleteNonExistentAccount() {
        util = new JsonFileUtil();
        // Setup a test JSON file with predefined accounts
        try (Writer writer = new FileWriter(TEST_FILE)) {
            JsonArray jsonArray = new JsonArray();
            JsonObject jsonObject1 = new JsonObject();
            jsonObject1.addProperty("accountNumber", 123456);
            jsonArray.add(jsonObject1);
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("accountNumber", 789101);
            jsonArray.add(jsonObject2);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonArray, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Attempt to delete a non-existent account
        assertFalse(util.deleteAccountFromJsonFile(TEST_FILE, 111222), "Should return false when trying to delete a non-existent account");
    }

}