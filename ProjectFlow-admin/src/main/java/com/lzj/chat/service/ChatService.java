package com.lzj.chat.service;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class ChatService {
    private final WebClient webClient;

    public ChatService() {
        this.webClient = WebClient.builder().baseUrl("http://localhost:8088").build();
    }

    public ResponseEntity<Flux<String>> chatWithAI(String message) {
        Flux<String> responseFlux = webClient.get()
                .uri("/ai/chat?message=" + message)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class);  // 处理流式数据

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)  // 指定返回的流式数据类型
                .body(responseFlux);
    }
}
