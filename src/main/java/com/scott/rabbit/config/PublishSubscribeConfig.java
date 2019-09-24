package com.scott.rabbit.config;

import com.scott.rabbit.utils.DefaultString;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//发布订阅模式的配置,包括两个队列和对应的订阅者,发布者的交换机类型使用fanout(子网广播),两根网线binding用来绑定队列到交换机
@Configuration
public class PublishSubscribeConfig {

    @Bean
    public Queue myQueue1() {
        Queue queue = new Queue(DefaultString.QUENUE_FIRST_ONE);
        return queue;
    }

    @Bean
    public Queue myQueue2() {
        Queue queue = new Queue(DefaultString.QUENUE_FIRST_TWO);
        return queue;
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        FanoutExchange fanoutExchange = new FanoutExchange(DefaultString.QUENUE_FANOUT);
        return fanoutExchange;
    }

    @Bean
    public Binding binding1() {
        Binding binding = BindingBuilder.bind(myQueue1()).to(fanoutExchange());
        return binding;
    }

    @Bean
    public Binding binding2() {
        Binding binding = BindingBuilder.bind(myQueue2()).to(fanoutExchange());
        return binding;
    }

}
