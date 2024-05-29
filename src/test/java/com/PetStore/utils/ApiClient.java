package com.PetStore.utils;

import com.PetStore.dto.OrderDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApiClient {
    private static String BASE_URL;

    static {
        String env = System.getProperty("env");
        if (env == null || env.isEmpty()) {
            throw new RuntimeException("Environment property 'env' is not specified. Please set it using -Denv=<environment>");
        }

        Properties properties = new Properties();
        try (InputStream input = ApiClient.class.getClassLoader().getResourceAsStream("env/" + env + ".properties")) {
            if (input == null) {
                throw new RuntimeException("Cannot find properties file for environment: " + env);
            }
            properties.load(input);
            BASE_URL = properties.getProperty("base.url");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file for environment: " + env, e);
        }
    }

    public static Response getInventory() {
        return RestAssured.get(BASE_URL + "/store/inventory");
    }

    public static Response createOrder(OrderDTO order) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(order)
                .post(BASE_URL + "/store/order");
    }

    public static Response getOrderById(long orderId) {
        return RestAssured.get(BASE_URL + "/store/order/" + orderId);
    }

    public static Response deleteOrderById(long orderId) {
        return RestAssured.delete(BASE_URL + "/store/order/" + orderId);
    }
}
