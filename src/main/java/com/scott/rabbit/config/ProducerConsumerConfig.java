package com.scott.rabbit.config;

import com.scott.rabbit.utils.DefaultString;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//生产者消费者模式的配置,包括一个队列和两个对应的消费者
//配置路由和通道
@Configuration
public class ProducerConsumerConfig {

    @Bean
    public Queue myQueue() {
        Queue queue = new Queue(DefaultString.QUENUE_FIRST);
        return queue;
    }

}

