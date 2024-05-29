package com.PetStore.pageObjects;

import io.restassured.response.Response;
import com.jayway.jsonpath.JsonPath;
import com.PetStore.dto.InventoryDTO;
import java.util.Map;

public class InventoryPage {

    private final Response response;

    public InventoryPage(Response response) {
        this.response = response;
    }

    public int getStatusCode() {
        return response.getStatusCode();
    }

    public String getHeader(String headerName) {
        return response.getHeader(headerName);
    }

    public InventoryDTO getInventoryDTO() {
        Map<String, Integer> inventory = JsonPath.read(response.getBody().asString(), "$");
        return new InventoryDTO(inventory);
    }
}
