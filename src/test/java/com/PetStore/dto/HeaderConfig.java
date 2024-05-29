package com.PetStore.dto;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HeaderConfig {
    private static final String FILE_PATH = "C:\\Users\\farru\\IdeaProjects\\PetStore\\src\\test\\java\\resources\\headerConfig.properties";
    private final Properties properties;

    public HeaderConfig() {
        properties = new Properties();
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream inputStream = HeaderConfig.class.getResourceAsStream(FILE_PATH)) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new RuntimeException("Properties file not found at: " + FILE_PATH);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file: " + FILE_PATH, e);
        }
    }

    public String getAccessControlAllowHeaders() {
        return properties.getProperty("accessControlAllowHeaders");
    }

    public String getAccessControlAllowMethods() {
        return properties.getProperty("accessControlAllowMethods");
    }

    public String getAccessControlAllowOrigin() {
        return properties.getProperty("accessControlAllowOrigin");
    }

    public String getContentType() {
        return properties.getProperty("contentType");
    }

    public static void main(String[] args) {
        HeaderConfig config = new HeaderConfig();
        System.out.println("Access-Control-Allow-Headers: " + config.getAccessControlAllowHeaders());
        System.out.println("Access-Control-Allow-Methods: " + config.getAccessControlAllowMethods());
        System.out.println("Access-Control-Allow-Origin: " + config.getAccessControlAllowOrigin());
        System.out.println("Content-Type: " + config.getContentType());
    }
}
