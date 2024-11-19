package com.lzj.test;


import org.flowable.dmn.api.DmnRepositoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Model;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlowableUiTest {

    private static final String FLOWABLE_TOKEN_URL = "http://localhost:8888/app/authentication";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("j_username", "admin");
        params.add("j_password", "admin");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Host", "localhost:8888"); // 设置 Host 头
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON)); // 使用 Arrays.asList 替代 List.of

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(FLOWABLE_TOKEN_URL, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                HttpStatus statusCode = response.getStatusCode();
                String responseBody = response.getBody();

                System.out.println("statusCode = " + statusCode.toString());
                System.out.println("response.getBody() = " + responseBody);

                // 获取 Set-Cookie 头部信息
                List<String> cookies = response.getHeaders().get("Set-Cookie");
                if (cookies != null && !cookies.isEmpty()) {
                    for (String cookie : cookies) {
                        System.out.println("cookie = " + cookie);
                        if (cookie.startsWith("FLOWABLE_REMEMBER_ME=")) {
                            String flowableRememberMe = cookie.split(";")[0];
                            System.out.println("FLOWABLE_REMEMBER_ME: " + flowableRememberMe);
                            break;
                        }
                    }
                } else {
                    System.out.println("No cookies found in the response.");
                }
            } else {
                throw new RuntimeException("Failed to generate token: " + response.getStatusCode() + " - " + response.getBody());
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

class Act_de_modelToXml{


}
