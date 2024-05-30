package com.PetStore.utils;

import org.testng.Assert;

import java.lang.reflect.Method;
import java.util.Properties;

public class TestUtils {

    public static void validateResponseHeaders(Object apiResponse, Properties headersProperties) {
        try {
            Method getHeaderMethod = apiResponse.getClass().getMethod("getHeader", String.class);

            Assert.assertEquals(getHeaderMethod.invoke(apiResponse, "access-control-allow-headers"), headersProperties.getProperty("access-control-allow-headers"), "access-control-allow-headers header value is incorrect");
            Assert.assertEquals(getHeaderMethod.invoke(apiResponse, "access-control-allow-methods"), headersProperties.getProperty("access-control-allow-methods"), "access-control-allow-methods header value is incorrect");
            Assert.assertEquals(getHeaderMethod.invoke(apiResponse, "access-control-allow-origin"), headersProperties.getProperty("access-control-allow-origin"), "access-control-allow-origin header value is incorrect");
            Assert.assertEquals(getHeaderMethod.invoke(apiResponse, "content-type"), headersProperties.getProperty("content-type"), "content-type header value is incorrect");
            Assert.assertNotNull(getHeaderMethod.invoke(apiResponse, "date"), "Date header is missing");
            Assert.assertTrue(((String) getHeaderMethod.invoke(apiResponse, "server")).startsWith(headersProperties.getProperty("server-prefix")), "Server header does not start with Jetty");

        } catch (Exception e) {
            throw new RuntimeException("Error validating response headers", e);
        }
    }
}
