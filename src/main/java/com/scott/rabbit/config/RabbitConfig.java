package com.scott.rabbit.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//连接rabbitMQ的基本配置
@Configuration
@EnableRabbit
public class RabbitConfig {
    @Value("${spring.rabbitmq.username}")
    public String username;
    @Value("${spring.rabbitmq.password}")
    public String password;
    @Value("${spring.rabbitmq.port}")
    public String port;
    @Value("${spring.rabbitmq.virtual-host}")
    public String virtualHost;
    @Value("${spring.rabbitmq.listener.simple.concurrency}")
    public String concurrency;
    @Value("${spring.rabbitmq.listener.simple.max-concurrency}")
    public String maxConcurrency;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setPort(Integer.parseInt(port));
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    //配置消费者监听的容器
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrentConsumers(Integer.parseInt(concurrency));
        factory.setMaxConcurrentConsumers(Integer.parseInt(maxConcurrency));
        //factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);//设置确认模式手工确认
        return factory;
    }
}
