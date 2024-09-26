package com.example.springboot.cloudrun.jobs.service;

import com.example.springboot.cloudrun.jobs.service.data.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
public class ApiCaller {
    private  final ApiResponse  apiResponse;

    public ApiResponse callApi(String accountPayload) throws IOException, InterruptedException {
        // Create an instance of HttpClient
        HttpClient client = HttpClient.newHttpClient();


        // Build a POST request with the JSON payload
        HttpRequest request = null;
        try {
            String microServicesB = "https://jsonplaceholder.typicode.com/posts";
            request = HttpRequest.newBuilder()
                                 .uri(new URI(microServicesB)) // Example API
                                 .header("Content-Type", "application/json")
                                 .POST(HttpRequest.BodyPublishers.ofString(accountPayload, StandardCharsets.UTF_8))
                                 .build();
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        apiResponse.setStatus(response.statusCode());
        apiResponse.setMessage(response.body());


        return apiResponse;
    }
}
