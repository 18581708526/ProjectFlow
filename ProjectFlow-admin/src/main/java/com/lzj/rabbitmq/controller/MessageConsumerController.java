package com.lzj.rabbitmq.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value ="/rabitmq")
public class MessageConsumerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @GetMapping(value ="/send/{message}/{fornum}")
    public String send(@PathVariable("message") String message,@PathVariable("fornum") Integer fornum){
        for(int i=0;i<fornum;i++){
            rabbitTemplate.convertAndSend("test.queue",message+(i+1));
        }
        return "success";
    }
}
