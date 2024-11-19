package com.lzj.controller;

import com.lzj.factory.AiChatFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
@CrossOrigin(origins = "*")
public class ChatController {
    private final AiChatFactory aiChatFactory;
    public ChatController(AiChatFactory aiServiceFactory) {
        this.aiChatFactory = aiServiceFactory;
    }
    @GetMapping(value = "/chat",
                produces = MediaType.TEXT_EVENT_STREAM_VALUE + ";charset=UTF-8")
    public ResponseEntity<Flux<String>> chat(@RequestParam(value="message") String message,
                                             @RequestParam(value="type") String type) {
        return aiChatFactory.getAiService(type).chat(message);
    }
}

