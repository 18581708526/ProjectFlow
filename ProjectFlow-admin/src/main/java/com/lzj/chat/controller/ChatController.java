package com.lzj.chat.controller;

import com.lzj.chat.conf.DeepseekClient;

import com.lzj.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/ask")
    public ResponseEntity<Flux<String>> ask(@RequestBody String message) {

        ResponseEntity<Flux<String>> response = chatService.chatWithAI(message);

        return response;
    }
//    @PostMapping("/ask")
//    public String askQuestion(@RequestBody String question) {
//
//        try {
//            String response = new
//                    DeepseekClient().getResponse(apiKey, question);
//            return response;
//        } catch (Exception e) {
//            return "出错了：" + e.getMessage();
//        }
//    }
}
