package com.PetStore.tests;

import com.PetStore.pageObjects.InventoryPage;
import com.PetStore.dto.InventoryDTO;
import com.PetStore.utils.ApiClient;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class InventoryTests {
    private Properties headersProperties;
    private InventoryPage inventoryPage;

    @BeforeClass
    public void setUpPage() {
        headersProperties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("headers.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find headers.properties");
            }
            headersProperties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load headers.properties", e);
        }
        Response response = ApiClient.getInventory();
        inventoryPage = new InventoryPage(response);
    }

    @Test
    public void testStatusCode() {
        Assert.assertEquals(inventoryPage.getStatusCode(), 200, "Status code is not 200");
    }

    @Test
    public void testResponseHeaders() {
        // Validate response headers
        Assert.assertEquals(inventoryPage.getHeader("access-control-allow-headers"), headersProperties.getProperty("access-control-allow-headers"), "access-control-allow-headers header value is incorrect");
        Assert.assertEquals(inventoryPage.getHeader("access-control-allow-methods"), headersProperties.getProperty("access-control-allow-methods"), "access-control-allow-methods header value is incorrect");
        Assert.assertEquals(inventoryPage.getHeader("access-control-allow-origin"), headersProperties.getProperty("access-control-allow-origin"), "access-control-allow-origin header value is incorrect");
        Assert.assertEquals(inventoryPage.getHeader("content-type"), headersProperties.getProperty("content-type"), "content-type header value is incorrect");
        Assert.assertNotNull(inventoryPage.getHeader("date"), "Date header is missing");
        Assert.assertTrue(inventoryPage.getHeader("server").startsWith(headersProperties.getProperty("server-prefix")), "Server header does not start with Jetty");
    }

    @Test
    public void testGetInventory() {
        InventoryDTO inventoryDTO = inventoryPage.getInventoryDTO();

        Assert.assertNotNull(inventoryDTO);

        // Validate that all fields have non-negative integer values
        for (Map.Entry<String, Integer> entry : inventoryDTO.getInventory().entrySet()) {
            Assert.assertTrue(entry.getValue() >= 0,
                    "Field " + entry.getKey() + " has an invalid value: " + entry.getValue());
        }
    }
}
