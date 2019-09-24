package com.scott.rabbit.listener;

import com.rabbitmq.client.Channel;
import com.scott.rabbit.entity.Mail;
import com.scott.rabbit.utils.DefaultString;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = DefaultString.QUENUE_FIRST)
public class QueueListener {
    private static Log log = LogFactory.getLog(QueueListener.class);

    @RabbitHandler
    public void displayMail(Mail mail, Channel channel, Message message) throws Exception {
        log.info("队列监听器0号收到消息：" + mail.toString());
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);//如果需要确认的要调用

//        or
//        log.info("队列监听器2号收到消息"+mail.toString());
    }



}
