package com.lzj.service.impl;

import com.lzj.constant.AiModel;
import com.lzj.service.AiService;
import com.lzj.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service(AiModel.LOCAL)
@Slf4j
public class AiOllameServiceImpl implements AiService {
    private final ChatClient chatclient;

    public AiOllameServiceImpl(ChatClient.Builder chatclient) {
        this.chatclient = chatclient.build();
    }

    @Override
    public ResponseEntity<Flux<String>> chat(String message) {
        try {
            Flux<String> response = chatclient.prompt(message).stream().content();
            // 打印响应数据
            //response.subscribe(data -> logger.info("Response data: {}", data));
            ResponseEntity<Flux<String>> body = ResponseEntity.ok()
                    .contentType(MediaType.TEXT_EVENT_STREAM) // 设置内容类型为文本事件流
                    .header(HttpHeaders.CONTENT_ENCODING, "utf-8") // 设置字符编码
                    .body(response);
            log.info("调用本地模型。。。{}", DateTimeUtil.getCurrentTime());
            return body;
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
