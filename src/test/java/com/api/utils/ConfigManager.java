package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static Properties properties;
    private static String environment = System.getProperty("env", "qa"); // Default to "qa" if no 'env' property is passed

    static {
        properties = new Properties();
        String propFileName = environment + ".properties";
        InputStream inputStream = null;

        try {
            // Try to load from src/test/resources/configs
            inputStream = ConfigManager.class.getClassLoader().getResourceAsStream("configs/" + propFileName);

            if (inputStream != null) {
                properties.load(inputStream);
                System.out.println("Loaded properties from: " + propFileName + " for environment: " + environment);
            } else {
                System.err.println("WARNING: Properties file '" + propFileName + "' not found in 'src/test/resources/configs'. Using default/empty properties.");
            }
        } catch (IOException e) {
            System.err.println("ERROR loading properties file: " + propFileName + ". " + e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    System.err.println("Error closing input stream: " + e.getMessage());
                }
            }
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            System.err.println("WARNING: Property '" + key + "' not found in " + environment + ".properties.");
        }
        return value;
    }

    // Optional: If you want to get a property with a default value
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    // Optional: Method to reload properties if needed (e.g., in a @BeforeSuite)
    public static void setEnvironment(String env) {
        if (!ConfigManager.environment.equalsIgnoreCase(env)) {
            ConfigManager.environment = env;
            properties = new Properties(); // Clear existing properties
            String propFileName = environment + ".properties";
            InputStream inputStream = null;

            try {
                inputStream = ConfigManager.class.getClassLoader().getResourceAsStream("configs/" + propFileName);

                if (inputStream != null) {
                    properties.load(inputStream);
                    System.out.println("Reloaded properties from: " + propFileName + " for environment: " + environment);
                } else {
                     System.err.println("WARNING: Properties file '" + propFileName + "' not found in 'src/test/resources/configs'. Using default/empty properties.");
                }
            } catch (IOException e) {
                System.err.println("ERROR reloading properties file: " + propFileName + ". " + e.getMessage());
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        System.err.println("Error closing input stream: " + e.getMessage());
                    }
                }
            }
        }
    }
}