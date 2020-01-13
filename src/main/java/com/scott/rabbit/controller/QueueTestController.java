package com.scott.rabbit.controller;

import com.scott.rabbit.entity.Mail;
import com.scott.rabbit.service.impl.ProducerImpl;
import com.scott.rabbit.service.impl.PublisherImpl;
import com.scott.rabbit.utils.DefaultString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueueTestController {
    @Autowired
    private ProducerImpl producer;
    @Autowired
    private PublisherImpl publisher;


    @RequestMapping(value = "/test0")
    public String test0(){
        for (int i = 0; i < 999; i++) {
            Mail mail = new Mail();
            mail.setCountry("中国");
            mail.setMailId("10001001" + String.valueOf(i));
            mail.setWeight(i);
            producer.sendMail(DefaultString.QUENUE_FIRST_ONE, mail);
        }
        return "OK";
    }

    @RequestMapping(value = "/test1")
    public String test1(){
        for (int i = 0; i < 999; i++) {
            Mail mail = new Mail();
            mail.setCountry("中国");
            mail.setMailId("10001001" + String.valueOf(i));
            mail.setWeight(i);
            producer.sendMail(DefaultString.QUENUE_FIRST, mail);
        }
        return "OK";
    }

    @RequestMapping(value = "/test2")
    public String test2(){
        String value = "{\"actionCode\":\"082\",\"billNo\":\"201912301001068771\",\"cardHolderMessage\":{\"city\":\"USA\",\"country\":\"USA\",\"email\":\"test@gmail.com\",\"firstName\":\"Xue\",\"ip\":\"192.168.0.55\",\"lastName\":\"laoBiao\",\"stage\":\"USA\",\"street\":\"shennandadao 9966\",\"zip\":\"12524\"},\"cardMessage\":{\"cardExpireMonth\":\"07\",\"cardExpireYear\":\"2024\",\"cardNo\":\"36259600300107\",\"serviceCode\":\"960\"},\"failFree\":0,\"feeRateBond\":0.006,\"feeRateMerchant\":0.006,\"freeFeeRate\":1.00,\"freezeFree\":0,\"functionCode\":\"100\",\"pointOfServiceCode\":\"900101194130\",\"refundFree\":0,\"remark\":\"remark\",\"riskFree\":0,\"serverUrl\":\"192.168.0.208:8989/acquirer-receiveCallback/receiveCallback\",\"serviceCode\":\"900101194130\",\"settleCurrency\":\"USD\",\"settleRate\":1.00,\"successFree\":0,\"tradeDataMessage\":{\"aiic\":\"00000367270\",\"approvalCode\":\"677331\",\"billAddress\":\"adagfdg\",\"businessCode\":\"1594\",\"cardOrgNo\":5,\"explain\":\"approved by Xpress (stand–in)\",\"fiic\":\"00000361589\",\"lableAmount\":96.62,\"lableCurrency\":\"USD\",\"machineIp\":\"192.168.0.55\",\"merNo\":\"000100000010006\",\"netWorkReferenceNo\":\"071577671261654\",\"recivingCode\":\"00000367270\",\"status\":1,\"tradeAmount\":96.62,\"tradeDateTimeGmt\":1577642472326,\"tradeDateTimeLocal\":1577671270761,\"tradeNo\":\"201912301001099440000000000002\",\"traderCurrency\":\"USD\"},\"tradeNo\":\"201912301001099440000000000002\"}";
        for (int i = 0; i < 999; i++) {
            producer.sendMessage(DefaultString.QUENUE_FIRST_TWO, value);
        }
        return value;
    }

    @RequestMapping(value = "/testAll")
    public void testAll(){
        producer.sendMessage(DefaultString.TOPIC_QUEUE_ONE,"topic1");
        producer.sendMessage(DefaultString.TOPIC_QUEUE_TWO,"topic2");
        producer.sendMessage(DefaultString.QUENUE_DIRECT,"direct");
        producer.sendMessage(DefaultString.QUENUE_DIRECT_QUEUE_ONE,"direct1");
        producer.sendMessage(DefaultString.QUENUE_DIRECT_QUEUE_TWO,"direct2");
    }
}
