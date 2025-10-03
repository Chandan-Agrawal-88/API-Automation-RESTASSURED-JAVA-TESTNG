package com.api.utils;

import com.github.javafaker.Faker;
import org.json.JSONObject;
import org.json.JSONArray; // Still needed if other methods use it

public class TestDataGenerator {
 private static final Faker faker = new Faker();

 public static JSONObject generatePetPayload() {
     // ... (existing pet generation code) ...
     JSONObject category = new JSONObject();
     category.put("id", faker.number().randomDigit());
     category.put("name", faker.animal().name());

     JSONArray photoUrls = new JSONArray();
     photoUrls.put(faker.internet().url());

     JSONObject tag = new JSONObject();
     tag.put("id", faker.number().randomDigit());
     tag.put("name", faker.lorem().word());

     JSONArray tags = new JSONArray();
     tags.put(tag);

     JSONObject pet = new JSONObject();
     pet.put("id", faker.number().randomNumber()); // Using randomNumber for potentially larger IDs
     pet.put("category", category);
     pet.put("name", faker.dog().name());
     pet.put("photoUrls", photoUrls);
     pet.put("tags", tags);
     pet.put("status", faker.options().option("available", "pending", "sold"));

     return pet;
 }

 public static JSONObject generateUserPayload() {
     JSONObject user = new JSONObject();
     user.put("id", faker.number().randomNumber());
     user.put("username", faker.name().username());
     user.put("firstName", faker.name().firstName());
     user.put("lastName", faker.name().lastName());
     user.put("email", faker.internet().emailAddress());
     user.put("password", faker.internet().password(8, 12, true, true, true)); // min 8, max 12, include uppercase, special, digit
     user.put("phone", faker.phoneNumber().phoneNumber());
     user.put("userStatus", faker.number().numberBetween(0, 3)); // Assuming status is an integer 0, 1, 2, 3

     return user;
 }
}