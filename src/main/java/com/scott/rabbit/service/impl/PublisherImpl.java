package com.scott.rabbit.service.impl;

import com.scott.rabbit.entity.Mail;
import com.scott.rabbit.service.Publisher;
import com.scott.rabbit.utils.DefaultString;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("publisher")
public class PublisherImpl implements Publisher {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void publishMail(Mail mail) {
        rabbitTemplate.convertAndSend(DefaultString.QUENUE_FANOUT, "", mail);
    }

    public void senddirectMail(Mail mail, String routingkey) {
        rabbitTemplate.convertAndSend(DefaultString.QUENUE_DIRECT, routingkey, mail);
    }

    public void sendtopicMail(Mail mail, String routingkey) {
        rabbitTemplate.convertAndSend(DefaultString.TOPIC_NAME, routingkey, mail);
    }
}
