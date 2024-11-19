package com.lzj.test;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static com.lzj.workflow.common.util.FlowableUiTokenUtil.Cookie;
import static com.lzj.workflow.common.util.FlowableUiTokenUtil.SETCOOKIES;

/**
 * 通过下载文件来部署流程
 * @Author lzj
 */
@Slf4j
public class ProcessDeploymTest {
    private static final String FLOWABLE_TOKEN_URL = "http://localhost:8888/app/authentication";
    public ResponseEntity<Void> ProcessUilogin(){
    RestTemplate restTemplate = new RestTemplate();

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("j_username", "admin");
        params.add("j_password", "admin");

    HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Host", "localhost:8888");
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        try {
        ResponseEntity<String> response = restTemplate.postForEntity(FLOWABLE_TOKEN_URL, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {

            List<String> cookies = response.getHeaders().get("Set-Cookie");
            if (cookies != null && !cookies.isEmpty()) {
                for (String cookie : cookies) {
                    if (cookie.startsWith("FLOWABLE_REMEMBER_ME=")) {
                        headers.add("Set-Cookie", cookie);
                        break;
                    }
                }
            } else {
                log.info("没有该Cookie信息，{}",cookies);
            }
            //实现无感登录后自动部署文件 ===========================
            String url = "http://localhost:8888/modeler-app/rest/models/0bbd5253-aa5a-11ef-9191-005056c00001/bpmn20";
            // 创建HTTP客户端
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                // 创建GET请求
                HttpGet request = new HttpGet(url);
                request.addHeader(Cookie, headers.getFirst(SETCOOKIES));
                // 执行请求
                try (CloseableHttpResponse httpresponse = httpClient.execute(request)) {
                    // 检查响应状态
                    if (httpresponse.getStatusLine().getStatusCode() == 200) {
                        // 获取响应内容
                        InputStream inputStream = httpresponse.getEntity().getContent();
                        //TODO 待删除，只是为了不报错
                        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
                        RepositoryService repositoryService = engine.getRepositoryService();
                        // 部署流程定义
                        Deployment deploy1 = repositoryService.createDeployment()
                                .addInputStream("process.bpmn20.xml", inputStream)
                                .name("测试流程")
                                .deploy();

                        log.info("流程定义已成功部署，流程实例id:{}", deploy1.getId());
                    } else {
                        log.info("无法下载文件，HTTP状态码 {}", httpresponse.getStatusLine().getStatusCode());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("获取token失败： " + response.getStatusCode() + " - " + response.getBody());
        }
    } catch (Exception e) {
        log.error("错误信息：{}",e.getMessage());
    }
        return new ResponseEntity<>(headers, HttpStatus.OK);
}
}
