package com.scott.rabbit.listener;

import com.scott.rabbit.utils.DefaultString;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = DefaultString.TOPIC_QUEUE_TWO)
public class QueueListener2 {
    private static Log log = LogFactory.getLog(QueueListener2.class);

    @RabbitHandler
    public void displayMessage(String message) throws Exception {
        log.info("队列监听器2号收到消息：" + message);
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);//如果需要确认的要调用

//        or
//        log.info("队列监听器2号收到消息"+mail.toString());
    }



}
