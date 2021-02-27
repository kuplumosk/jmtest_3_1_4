package com.kuplumosk.rest.controllers;

import com.kuplumosk.rest.entitys.User;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestClient {

    private static final String ENDPOINT = "http://91.241.64.178:7081/api/users";

    private static final Byte USER_AGE = 25;
    private static final Long USER_ID = 3L;

    private static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        RestClient restClient = new RestClient();

        String header = restClient.getEmployees();

        restClient.createEmployee(header);
        restClient.updateEmployee(header);
        restClient.deleteEmployee(header);
    }

    private String getEmployees() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> result = restTemplate.exchange(ENDPOINT, HttpMethod.GET, entity,
            String.class);

        return result.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
    }

    private void createEmployee(String header) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", header);

        User newEmployee = new User(USER_ID, "James", "Brown", USER_AGE);

        HttpEntity<User> requestBody = new HttpEntity<>(newEmployee, headers);

        ResponseEntity<String> response = restTemplate.exchange(ENDPOINT, HttpMethod.POST, requestBody, String.class);

        System.out.println("RESPONSE " + response.getBody());
    }

    private void updateEmployee(String header) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", header);

        User updatedEmployee = new User(USER_ID, "Thomas", "Shelby", USER_AGE);

        HttpEntity<User> requestBody = new HttpEntity<>(updatedEmployee, headers);

        ResponseEntity<String> response = restTemplate.exchange(ENDPOINT, HttpMethod.PUT, requestBody, String.class);
        System.out.println("RESPONSE " + response.getBody());
    }

    private void deleteEmployee(String header) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", header);

        HttpEntity<User> requestBody = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(ENDPOINT + "/" + USER_ID, HttpMethod.DELETE,
            requestBody, String.class);
        System.out.println("RESPONSE " + response.getBody());
    }
}
