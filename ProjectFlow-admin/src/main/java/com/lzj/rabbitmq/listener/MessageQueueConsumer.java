package com.lzj.rabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Date;

import static com.lzj.common.utils.Threads.sleep;

@Component
public class MessageQueueConsumer {
    private int count;

    @RabbitListener(queues = "test.queue")
    public void onMessage(String message) {

        this.count++;
        System.out.println("接收到消息：" + message +"time:"+ LocalTime.now()+"\n" +
                " 线程："+Thread.currentThread().getName()+"序号："+count);
    }
}

