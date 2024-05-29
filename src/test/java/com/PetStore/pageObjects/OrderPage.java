package com.PetStore.pageObjects;


import com.PetStore.dto.OrderDTO;
import io.restassured.response.Response;


public class OrderPage {

    private final Response response;

    public OrderPage(Response response) {
        this.response = response;
    }

    public int getStatusCode() {
        return response.getStatusCode();
    }

    public OrderDTO getOrderResponse() {
        return response.as(OrderDTO.class);
    }
}
