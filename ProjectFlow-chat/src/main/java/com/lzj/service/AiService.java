package com.lzj.service;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
//统一AI聊天接口
public interface AiService {
    ResponseEntity<Flux<String>> chat(String message);
}
