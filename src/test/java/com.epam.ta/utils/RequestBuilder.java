package com.epam.ta.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.io.IOException;


public class RequestBuilder {
    private static final Logger logger = LogManager.getLogger("AllureLogs");

    @Step("Sending request")
    public static HttpResponse execute(HttpUriRequest request) throws IOException {
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        logger.info("Request " + request.toString() + " sent");
        Allure.addAttachment("Raw response", "application/json", response.toString());
        return response;
    }
}
