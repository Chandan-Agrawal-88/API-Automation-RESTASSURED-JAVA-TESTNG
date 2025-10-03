package com.api.tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import org.testng.Assert;

import com.api.utils.FileHandlingUtils;
import com.api.utils.ConfigManager;

import java.io.IOException;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Epic("User Management API")
@Feature("User Creation")
public class CreateUserTestAPI5 {

    @Test(description = "Verify user creation API is working based on properties file configuration")
    @Story("As a user, I want to create new accounts from a list") // Updated story to reflect array/list creation
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Chandan Agrawal")
    public void userCreationTest() throws IOException {
        baseURI = ConfigManager.getProperty("baseURI");
        String createUserEndpoint = ConfigManager.getProperty("createUserEndpoint");

        System.out.println("Loaded Base URI from config: " + baseURI);
        System.out.println("Loaded Create User Endpoint from config: " + createUserEndpoint);

        String jsonFilePath = "src/test/resources/userOperations/createUserPayload.json";
        String requestBody = FileHandlingUtils.readJsonFile(jsonFilePath);

        // Assuming your payload is an array of users, you might want to modify
        // multiple usernames/emails if there are multiple objects in the array.
        // For a single object in array:
        requestBody = requestBody.replace("testuser", "testuser" + System.currentTimeMillis());
        requestBody = requestBody.replace("john.doe@example.com", "john.doe." + System.currentTimeMillis() + "@example.com");


        Response response = given()
            .header("Content-Type", "application/json")
            .body(requestBody)
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

        System.out.println("Response Body:\n" + response.asPrettyString());

        int responseCode = response.jsonPath().getInt("code");
        String responseType = response.jsonPath().getString("type");
        String responseMessage = response.jsonPath().getString("message");

        Assert.assertNotNull(responseCode, "Response should contain a 'code' field.");
        Assert.assertNotNull(responseType, "Response should contain a 'type' field.");
        Assert.assertNotNull(responseMessage, "Response should contain a 'message' field.");

        Assert.assertEquals(responseCode, 200, "Expected response 'code' to be 200.");
        Assert.assertEquals(responseType, "unknown", "Expected response 'type' to be 'unknown'.");
        // --- MODIFIED ASSERTION FOR MESSAGE FIELD ---
        Assert.assertEquals(responseMessage, "ok", "Expected response 'message' to be 'ok'.");
        // You can remove the try-catch block for parsing long ID as it's no longer applicable
        // Or keep it if you need dynamic logic for different endpoints.
    }
}