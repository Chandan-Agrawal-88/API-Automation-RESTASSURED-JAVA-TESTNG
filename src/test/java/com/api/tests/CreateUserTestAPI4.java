package com.api.tests;

import org.testng.annotations.Test;
// No longer need: import io.restassured.RestAssured; // We'll replace this with static imports
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import com.api.utils.FileHandlingUtils;

import java.io.IOException;

// Static imports for RestAssured - ADD THESE:
import static io.restassured.RestAssured.*; // Imports all static methods from RestAssured

public class CreateUserTestAPI4 {

    @Test(description = "Verify if user creation API is working...")
    public void userCreationTest() throws IOException {
        // baseURI can also be statically imported or set globally for the suite
        baseURI = "https://petstore.swagger.io"; // No need for RestAssured.baseURI anymore

        String jsonFilePath = "src/test/resources/createUserPayload.json";
        String requestBody = FileHandlingUtils.readJsonFile(jsonFilePath);

        requestBody = requestBody.replace("testuser", "testuser" + System.currentTimeMillis());
        requestBody = requestBody.replace("john.doe@example.com", "john.doe." + System.currentTimeMillis() + "@example.com");

        // Now you can use given() directly
        RequestSpecification request = given() // No "RestAssured." prefix needed
            .header("Content-Type", "application/json")
            .body(requestBody);

        // And post() directly
        Response response = request.post("/v2/user"); // No "RestAssured." prefix needed for post, get, put, delete etc.

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body:\n" + response.asPrettyString());

        // --- ASSERTIONS ---
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