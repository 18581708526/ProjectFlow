package com.lzj.workflow.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class FlowableUiTokenUtil {
    public static String FLOWABLE_TOKEN_URL;
    public static String FLOW_LOGINNAME;
    public static String FLOW_LOGINPWD;
    public static String FLOW_IP;
    public static String FLOW_COOKIES_PRIFIX;
    public static final String SETCOOKIES = "Set-Cookie";
    public static final String Cookie = "Cookie";
    public static final String COOKIE_HOST = "Host";
    public static final String DOWNLOAD_URL_STRAT = "http://localhost:8888/modeler-app/rest/models/";
    public static final String DOWNLOAD_URL_END="/bpmn20";
    public static final String DEPLOYMENT_FILE_FIX=".bpmn20.xml";
    @Value("${lzj.flow.loginapi}")
    public void setFLOWABLE_TOKEN_URL(String FLOWABLE_TOKEN_URL) {
        FlowableUiTokenUtil.FLOWABLE_TOKEN_URL = FLOWABLE_TOKEN_URL;
    }
    @Value("${lzj.flow.loginname}")
    public void setFLOW_LOGINNAME(String FLOW_LOGINNAME) {
        FlowableUiTokenUtil.FLOW_LOGINNAME = FLOW_LOGINNAME;
    }

    @Value("${lzj.flow.loginpwd}")
    public void setFLOW_LOGINPWD(String FLOW_LOGINPWD) {
        FlowableUiTokenUtil.FLOW_LOGINPWD = FLOW_LOGINPWD;
    }

    @Value("${lzj.flow.ip}")
    public void setFLOW_IP(String FLOW_IP) {
        FlowableUiTokenUtil.FLOW_IP = FLOW_IP;
    }

    @Value("${lzj.flow.cookies_prifix}")
    public void setFLOW_COOKIES_PRIFIX(String FLOW_COOKIES_PRIFIX) {
        FlowableUiTokenUtil.FLOW_COOKIES_PRIFIX = FLOW_COOKIES_PRIFIX;
    }
    public static HttpHeaders GetToken(String username) {

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(FLOW_LOGINNAME, username);
        params.add(FLOW_LOGINPWD, username);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set(COOKIE_HOST, FLOW_IP);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(FLOWABLE_TOKEN_URL, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {

                List<String> cookies = response.getHeaders().get(SETCOOKIES);
                if (cookies != null && !cookies.isEmpty()) {
                    for (String cookie : cookies) {
                        if (cookie.startsWith(FLOW_COOKIES_PRIFIX)) {
                            headers.add(SETCOOKIES, cookie);
                            break;
                        }
                    }
                } else {
                    log.info("没有该Cookie信息，{}",cookies);
                }
            } else {
                throw new RuntimeException("获取token失败： " + response.getStatusCode() + " - " + response.getBody());
            }
        } catch (Exception e) {
            log.error("错误信息：{}",e.getMessage());
        }
        return headers;
    }
}

