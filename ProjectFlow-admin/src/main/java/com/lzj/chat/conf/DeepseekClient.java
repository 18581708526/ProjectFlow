package com.lzj.chat.conf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.lzj.chat.domain.DeepseekRequest;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DeepseekClient {
    //如果使用硅基平台，API_URL修改为 "https://api.siliconflow.cn/v1/chat/completions"
   // private static final String API_URL = "https://api.deepseek.com/v1/chat/completions";
    private static final String API_URL = "https://api.siliconflow.cn/v1/chat/completions";

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();
    private final Gson gson = new Gson();


    public String getResponse(String apiKey, String prompt) throws IOException {
        // 构建请求体
        DeepseekRequest.Message message = DeepseekRequest.Message.builder()
                .role("user")
                .content(prompt).build();
        //如果使用硅基平台，model修改为 "deepseek-ai/DeepSeek-V3"
        DeepseekRequest requestBody = DeepseekRequest.builder()
                //.model("deepseek-chat")
                .model("deepseek-ai/DeepSeek-V2.5")

                .messages(Collections.singletonList(message))
                .build();

        // 创建HTTP请求
        Request request = new Request.Builder()
                .url(API_URL)
                .post(RequestBody.create(gson.toJson(requestBody), MediaType.get("application/json")))
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        // 发送请求并处理响应
        try (Response responsersp = client.newCall(request).execute()) {
            if (!responsersp.isSuccessful()) {
                throw new IOException("Unexpected code " + responsersp);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            DeepseekResponse response = objectMapper.readValue(responsersp.body().string(), DeepseekResponse.class);
            String content="";
            if (response.getChoices() != null && !response.getChoices().isEmpty()) {
                content = response.getChoices().get(0).getMessage().getContent();
                log.debug("response:{}",content);
            }
            return content;
        }

    }
    }



@JsonIgnoreProperties(ignoreUnknown = true)
 class DeepseekResponse {
    private List<Choice> choices;

    public List<Choice> getChoices() {
        return choices;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Choice {
    private Message message;

    public Message getMessage() {
        return message;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Message {
    private String content;

    public String getContent() {
        return content;
    }
}
