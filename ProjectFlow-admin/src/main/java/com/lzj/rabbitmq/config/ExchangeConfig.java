package com.lzj.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeConfig {

    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange("test.exchange");
    }

    @Bean
    public Queue queue1() {
        return new Queue("test.queue1");
    }

    @Bean
    public Queue queue2() {
        return new Queue("test.queue2");
    }

    @Bean
    public Binding binding1(Queue queue1, FanoutExchange exchange) {
        return BindingBuilder.bind(queue1).to(exchange);
    }

    @Bean
    public Binding binding2(Queue queue2, FanoutExchange exchange) {
        return BindingBuilder.bind(queue2).to(exchange);
    }

}
