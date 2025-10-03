package com.api.tests;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CreateUserTestAPI3 {

    // Utility method to read content from a file
    public static String readJsonFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    @Test(description = "Verify if user creation API is working...")
    public void userCreationTest() throws IOException { // Add throws IOException here
        baseURI = "https://petstore.swagger.io";

        // 1. Get the path to your JSON file
        // For Maven/Gradle projects, resources are on the classpath, so you can load them like this:
        // String jsonFilePath = getClass().getClassLoader().getResource("createUserPayload.json").getFile();
        // If 'resources' is directly in your project root or not a classpath root:
        String jsonFilePath = "src/test/resources/createUserPayload.json"; // Adjust path as needed

        // 2. Read the JSON content from the file
        String requestBody = readJsonFile(jsonFilePath);

        // Optional: If you need to make values dynamic (e.g., unique username/email)
        // You can replace placeholders in the string:
        requestBody = requestBody.replace("testuser", "testuser" + System.currentTimeMillis());
        requestBody = requestBody.replace("john.doe@example.com", "john.doe." + System.currentTimeMillis() + "@example.com");


        RequestSpecification request = given().header("Content-Type", "application/json").body(requestBody); // Pass the read JSON string

        Response response = request.post("/v2/user");

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body:\n" + response.asPrettyString());

        // --- ASSERTIONS (as discussed previously) ---
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 for user creation.");

        int responseCode = response.jsonPath().getInt("code");
        String responseType = response.jsonPath().getString("type");
        String responseMessage = response.jsonPath().getString("message");

        Assert.assertNotNull(responseCode, "Response should contain a 'code' field.");
        Assert.assertNotNull(responseType, "Response should contain a 'type' field.");
        Assert.assertNotNull(responseMessage, "Response should contain a 'message' field.");

        Assert.assertEquals(responseCode, 200, "Expected response 'code' to be 200.");
        Assert.assertEquals(responseType, "unknown", "Expected response 'type' to be 'unknown'.");
        Assert.assertFalse(responseMessage.isEmpty(), "Response 'message' should not be empty.");

        try {
            long parsedId = Long.parseLong(responseMessage);
            Assert.assertTrue(parsedId > 0, "Parsed message should be a positive ID.");
            System.out.println("Successfully parsed message as ID: " + parsedId);
        } catch (NumberFormatException e) {
            Assert.fail("Response 'message' could not be parsed as a long ID: " + responseMessage);
        }
    }
}