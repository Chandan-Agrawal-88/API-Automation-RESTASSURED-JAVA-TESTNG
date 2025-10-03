package com.api.utils; // New package declaration

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHandlingUtils {

    // Utility method to read content from a file as a String
    public static String readJsonFile(String filePath) throws IOException {
        // It's good practice to ensure the file exists and is readable,
        // but for simplicity, we'll let Files.readAllBytes handle potential FileNotFoundException.
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    // You can add other file-related utility methods here if needed
    // For example:
    // public static Properties readPropertiesFile(String filePath) { ... }
}