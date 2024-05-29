package com.PetStore.tests;

import com.PetStore.dto.OrderDTO;
import com.PetStore.pageObjects.OrderPage;
import com.PetStore.utils.ApiClient;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class OrderTests {

    private OrderDTO createRandomOrder() {
        List<String> statuses = Arrays.asList("placed", "approved", "delivered");
        OrderDTO order = new OrderDTO();
        order.setId((int) ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE));
        order.setPetId((int) ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE));
        order.setQuantity(ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE));
        order.setShipDate(LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ISO_DATE_TIME));
        order.setStatus(statuses.get(ThreadLocalRandom.current().nextInt(statuses.size())));
        order.setComplete(ThreadLocalRandom.current().nextBoolean());
        return order;
    }
    @Test
    public void testCreateOrder() {
        OrderDTO orderRequest = createRandomOrder();

        Response response = ApiClient.createOrder(orderRequest);
        OrderPage orderPage = new OrderPage(response);


        Assert.assertEquals(orderPage.getStatusCode(), 200, "Status code is not 200");

        OrderDTO orderResponse = orderPage.getOrderResponse();

        // Validate the response body
        Assert.assertNotNull(orderResponse, "Order response is null");

        // If the request ID is 0, check that the response ID is non-zero
        if (orderRequest.getId() == 0) {
            Assert.assertTrue(orderResponse.getId() != 0, "Order ID is not generated by the server");
        } else {
            // Otherwise, validate against the request ID
            Assert.assertEquals(orderResponse.getId(), orderRequest.getId(), "Order ID does not match");
        }

        Assert.assertEquals(orderResponse.getPetId(), orderRequest.getPetId(), "Pet ID does not match");
        Assert.assertEquals(orderResponse.getQuantity(), orderRequest.getQuantity(), "Quantity does not match");

        // Adjusting ship date comparison to handle the different formats
        String expectedShipDate = orderRequest.getShipDate().substring(0, 19) + "+0000";
        String actualShipDate = orderResponse.getShipDate().substring(0, 19) + "+0000";
        Assert.assertEquals(actualShipDate, expectedShipDate, "Ship date does not match");

        Assert.assertEquals(orderResponse.getStatus(), orderRequest.getStatus(), "Status does not match");
        Assert.assertEquals(orderResponse.isComplete(), orderRequest.isComplete(), "Complete status does not match");
    }

    @Test
    public void testGetOrderById() {
        OrderDTO orderRequest = createRandomOrder();
        ApiClient.createOrder(orderRequest);
        Response response = ApiClient.getOrderById(orderRequest.getId());
        OrderDTO fetchedOrder = response.as(OrderDTO.class);

        Assert.assertEquals(orderRequest.getId(), fetchedOrder.getId());
        Assert.assertEquals(orderRequest.getPetId(), fetchedOrder.getPetId());
        Assert.assertEquals(orderRequest.getQuantity(), fetchedOrder.getQuantity());
        // Adjusting ship date comparison to handle the different formats
        String expectedShipDate = orderRequest.getShipDate().substring(0, 19) + "+0000";
        String actualShipDate = fetchedOrder.getShipDate().substring(0, 19) + "+0000";
        Assert.assertEquals(actualShipDate, expectedShipDate, "Ship date does not match");
        Assert.assertEquals(orderRequest.getStatus(), fetchedOrder.getStatus());
        Assert.assertEquals(orderRequest.isComplete(), fetchedOrder.isComplete());
    }
    @Test
    public void testGetOrderNotFound() {
        OrderDTO orderRequest = createRandomOrder();
        ApiClient.createOrder(orderRequest);
        ApiClient.deleteOrderById(orderRequest.getId());
        Response response = ApiClient.getOrderById(orderRequest.getId());
        OrderDTO fetchedOrder = response.as(OrderDTO.class);
        int statusCode = response.getStatusCode();

        Assert.assertEquals(statusCode, 404, "Status code is not 404");
        Assert.assertEquals("Order not found", fetchedOrder.getMessage());
        Assert.assertEquals(1, fetchedOrder.getCode());
        Assert.assertEquals("error", fetchedOrder.getType());
    }
    @Test
    public void testDeleteOrderById() {
        OrderDTO orderRequest = createRandomOrder();
        ApiClient.createOrder(orderRequest);
        Response response = ApiClient.deleteOrderById(orderRequest.getId());
        OrderDTO fetchedOrder = response.as(OrderDTO.class);

        Assert.assertEquals(String.valueOf(orderRequest.getId()), fetchedOrder.getMessage());
        Assert.assertEquals(200, fetchedOrder.getCode());
        Assert.assertEquals("unknown", fetchedOrder.getType());
    }
    @Test
    public void testDeleteOrderNotFound() {
        OrderDTO orderRequest = createRandomOrder();
        ApiClient.createOrder(orderRequest);
        ApiClient.deleteOrderById(orderRequest.getId());
        Response response = ApiClient.deleteOrderById(orderRequest.getId());
        OrderDTO fetchedOrder = response.as(OrderDTO.class);
        int statusCode = response.getStatusCode();

        Assert.assertEquals(statusCode, 404, "Status code is not 404");
        Assert.assertEquals("Order Not Found", fetchedOrder.getMessage());
        Assert.assertEquals(404, fetchedOrder.getCode());
        Assert.assertEquals("unknown", fetchedOrder.getType());
    }
}