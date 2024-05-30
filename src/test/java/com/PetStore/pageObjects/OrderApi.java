package com.PetStore.pageObjects;


import com.PetStore.dto.OrderDTO;
import io.restassured.response.Response;


public class OrderApi {

    private final Response response;

    public OrderApi(Response response) {
        this.response = response;
    }
    public int getStatusCode() {
        return response.getStatusCode();
    }

    public OrderDTO getOrderResponse() {
        return response.as(OrderDTO.class);
    }
}
