package com.api.tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import org.testng.annotations.Parameters;
import io.restassured.response.Response;
import org.testng.Assert;

import com.api.utils.ConfigManager;
import com.api.utils.TestDataGenerator; // Import your TestDataGenerator
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONObject; // Import org.json.JSONObject

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Epic("User Management API")
@Feature("User Creation")
public class CreateUserTestAPI3 {

    @Test(description = "Verify user creation API is working based on TestDataGenerator for multiple users")
    @Story("As an admin, I want to create multiple new accounts dynamically using TestDataGenerator")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Chandan Agrawal")
    @Parameters("numberOfUsers")
    public void userCreationTest(int numberOfUsers) throws IOException {
        baseURI = ConfigManager.getProperty("baseURI");
        String createUserEndpoint = ConfigManager.getProperty("createUserEndpoint");

        System.out.println("CKA API Automation Framework - Loaded Base URI from config: " + baseURI);
        System.out.println("CKA API Automation Framework - Loaded Create User Endpoint from config: " + createUserEndpoint);
        System.out.println("CKA API Automation Framework - Number of users to create (from TestNG parameter): " + numberOfUsers);

        // No need to read a base JSON file anymore, as TestDataGenerator handles it
        // String baseJsonFilePath = "src/test/resources/userOperations/createUserPayload.json";
        // String baseUserPayloadString = FileHandlingUtils.readJsonFile(baseJsonFilePath);
        // JsonArray baseJsonArray = JsonParser.parseString(baseUserPayloadString).getAsJsonArray();
        // JsonObject baseUserObject = baseJsonArray.get(0).getAsJsonObject();


        List<JsonObject> usersList = new ArrayList<>();

        // Dynamically create multiple unique user objects using TestDataGenerator
        for (int i = 0; i < numberOfUsers; i++) {
            JSONObject generatedUserPayload = TestDataGenerator.generateUserPayload();

            // Convert org.json.JSONObject to com.google.gson.JsonObject
            // This is necessary because the rest of your framework uses Gson's JsonObject
            Gson gson = new Gson();
            JsonObject newUser = gson.fromJson(generatedUserPayload.toString(), JsonObject.class);

            // You can still override specific properties if needed after generation,
            // but the Faker-generated data is usually unique enough.
            // For example, if you specifically needed a common prefix or structure:
            // newUser.addProperty("username", "prefix_" + newUser.get("username").getAsString());

            usersList.add(newUser);
        }

        Gson gson = new Gson();
        String finalJsonArrayBody = gson.toJson(usersList);

        System.out.println("CKA API Automation Framework - Generated Request Body:\n" + finalJsonArrayBody);

        Response response = given()
            .header("Content-Type", "application/json")
            .body(finalJsonArrayBody)
        .when()
            .post(createUserEndpoint)
        .then()
            .statusCode(200)
            .header("access-control-allow-headers", equalTo("Content-Type, api_key, Authorization"))
            .header("access-control-allow-methods", equalTo("GET, POST, DELETE, PUT"))
            .header("access-control-allow-origin", equalTo("*"))
            .header("content-type", containsString("application/json"))
            .header("server", equalTo("Jetty(9.2.9.v20150224)"))
            .header("date", notNullValue())
            .log().all()
            .extract().response();

        System.out.println("CKA API Automation Framework - Response Body:\n" + response.asPrettyString());

        int responseCode = response.jsonPath().getInt("code");
        String responseType = response.jsonPath().getString("type");
        String responseMessage = response.jsonPath().getString("message");

        Assert.assertNotNull(responseCode, "Response should contain a 'code' field.");
        Assert.assertNotNull(responseType, "Response should contain a 'type' field.");
        Assert.assertNotNull(responseMessage, "Response should contain a 'message' field.");

        Assert.assertEquals(responseCode, 200, "Expected response 'code' to be 200.");
        Assert.assertEquals(responseType, "unknown", "Expected response 'type' to be 'unknown'.");
        Assert.assertEquals(responseMessage, "ok", "Expected response 'message' to be 'ok'.");
    }
}