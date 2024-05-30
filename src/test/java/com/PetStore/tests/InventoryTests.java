package com.PetStore.tests;

import com.PetStore.pageObjects.InventoryApi;
import com.PetStore.dto.InventoryDTO;
import com.PetStore.utils.ApiClient;
import com.PetStore.utils.TestUtils;
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
    private InventoryApi inventoryApi;

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
        inventoryApi = new InventoryApi(response);
    }

    @Test
    public void testStatusCode() {
        Assert.assertEquals(inventoryApi.getStatusCode(), 200, "Status code is not 200");
    }

    @Test
    public void testResponseHeaders() {
        TestUtils.validateResponseHeaders(inventoryApi, headersProperties);
    }

    @Test
    public void testGetInventory() {
        InventoryDTO inventoryDTO = inventoryApi.getInventoryDTO();

        Assert.assertNotNull(inventoryDTO);

        // Validate that all fields have non-negative integer values
        for (Map.Entry<String, Integer> entry : inventoryDTO.getInventory().entrySet()) {
            Assert.assertTrue(entry.getValue() >= 0,
                    "Field " + entry.getKey() + " has an invalid value: " + entry.getValue());
        }
    }
}
