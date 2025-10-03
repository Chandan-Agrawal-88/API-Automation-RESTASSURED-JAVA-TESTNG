package com.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CreateUserTestAPI {

    @Test(description = "Verify if user creation API is working...")
    public void userCreationTest() {
       baseURI = "https://petstore.swagger.io";

        RequestSpecification request = given().header("Content-Type", "application/json").body("{"
            + "\"id\": 0,"
            + "\"username\": \"testuser" + System.currentTimeMillis() + "\"," // Use unique username to avoid conflicts
            + "\"firstName\": \"John\","
            + "\"lastName\": \"Doe\","
            + "\"email\": \"john.doe." + System.currentTimeMillis() + "@example.com\","
            + "\"password\": \"password123\","
            + "\"phone\": \"123-456-7890\","
            + "\"userStatus\": 0"
            + "}");

        Response response = request.post("/v2/user"); // Assuming /v2/user for single user creation

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body:\n" + response.asPrettyString());

        // --- ASSERTIONS ---
        // 1. Assert Status Code (still expect 200 based on the last run)
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 for user creation.");

        // 2. Assert the presence of the 'code', 'type', and 'message' fields
        // Since 'id' and 'username' are not directly in the root of THIS response, we can't assert them directly like before.
        int responseCode = response.jsonPath().getInt("code");
        String responseType = response.jsonPath().getString("type");
        String responseMessage = response.jsonPath().getString("message"); // This will be the large number

        Assert.assertNotNull(responseCode, "Response should contain a 'code' field.");
        Assert.assertNotNull(responseType, "Response should contain a 'type' field.");
        Assert.assertNotNull(responseMessage, "Response should contain a 'message' field.");

        // 3. Assert the values of these fields based on what the API actually returns
        Assert.assertEquals(responseCode, 200, "Expected response 'code' to be 200.");
        Assert.assertEquals(responseType, "unknown", "Expected response 'type' to be 'unknown'.");
        // For the message, you might assert that it's a number, or just assert it's not null.
        // If the message is truly an ID, you might assert it's a positive number.
        // Here, we'll assert it's not empty, and potentially try to parse it as a long.
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