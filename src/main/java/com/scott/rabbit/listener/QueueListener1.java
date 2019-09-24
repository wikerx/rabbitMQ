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
@RabbitListener(queues = DefaultString.QUENUE_FIRST_ONE)
public class QueueListener1 {
    private static Log log = LogFactory.getLog(QueueListener1.class);

    @RabbitHandler
    public void displayMessage(Mail message) throws Exception {
        log.info("队列监听器1号收到消息：" + message.toString());
//        Channel channel = null;
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);//如果需要确认的要调用

//        void basicAck(long deliveryTag, boolean multiple) throws IOException;
//        参数解析
//        　　deliveryTag：该消息的index
//        　　multiple：是否批量处理.true:将一次性ack所有小于deliveryTag的消息。

//        or
//        log.info"队列监听器2号收到消息"+mail.toString());
    }



}
