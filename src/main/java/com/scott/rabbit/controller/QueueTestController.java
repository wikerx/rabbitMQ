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
        Mail mail = new Mail();
        mail.setCountry("中国");
        mail.setMailId("10001001");
        mail.setWeight(12.56);
        producer.sendMail(DefaultString.QUENUE_FIRST_ONE,mail);
        return "OK";
    }
    @RequestMapping(value = "/test1")
    public String test1(){
        Mail mail = new Mail();
        mail.setCountry("中国");
        mail.setMailId("10001001");
        mail.setWeight(12.56);
        producer.sendMail(DefaultString.QUENUE_FIRST,mail);
        return "OK";
    }
    @RequestMapping(value = "/test2")
    public String test2(){
        String value = "{\n" +
                "    \"additionalData\": \"附加数据.....\",\n" +
                "    \"billNo\": \"201909191630132790000000020645\",\n" +
                "    \"browserUrl\": \"https://www.baidu.com\",\n" +
                "    \"cardHolderMessage\": {\n" +
                "        \"city\": \"Auckland\",\n" +
                "        \"country\": \"US\",\n" +
                "        \"email\": \"funckyou@gmail.com\",\n" +
                "        \"firstName\": \"laoBiao\",\n" +
                "        \"ip\": \"192.168.0.55\",\n" +
                "        \"lastName\": \"Xue\"\n" +
                "    },\n" +
                "    \"cardMessage\": {\n" +
                "        \"cardExpireMonth\": \"02\",\n" +
                "        \"cardExpireYear\": \"2020\",\n" +
                "        \"cardNo\": \"4183680269057532\",\n" +
                "        \"serviceCode\": \"100\"\n" +
                "    },\n" +
                "    \"failFree\": 0.2,\n" +
                "    \"feeRateBond\": 0.2,\n" +
                "    \"feeRateMerchant\": 0.1,\n" +
                "    \"freeFeeRate\": 7.1035,\n" +
                "    \"freezeFree\": 2,\n" +
                "    \"functionCode\": \"100\",\n" +
                "    \"refundFree\": 2,\n" +
                "    \"remark\": \"REMARK\",\n" +
                "    \"riskFree\": 2,\n" +
                "    \"serverUrl\": \"https://www.baidu.com\",\n" +
                "    \"serviceCode\": \"180\",\n" +
                "    \"settleCurrency\": \"CNY\",\n" +
                "    \"settleRate\": 7.1035,\n" +
                "    \"successFree\": 0.2,\n" +
                "    \"systemTrackingAuditNumber\": \"4552200000\",\n" +
                "    \"terminalNumber\": \"0010\",\n" +
                "    \"tradeDataMessage\": {\n" +
                "        \"billAddress\": \"momo.com\",\n" +
                "        \"cardOrgNo\": 0,\n" +
                "        \"lableAmount\": 70,\n" +
                "        \"lableCurrency\": \"USD\",\n" +
                "        \"merNo\": \"100000010007\",\n" +
                "        \"status\": 1,\n" +
                "        \"tradeAmount\": 70,\n" +
                "        \"tradeDateTimeGmt\": 1568853013277,\n" +
                "        \"tradeDateTimeLocal\": 1568881812254,\n" +
                "        \"tradeNo\": \"201909191630122510000000020644\",\n" +
                "        \"traderCurrency\": \"USD\"\n" +
                "    },\n" +
                "    \"tradeNo\": \"201909191630122510000000020644\"\n" +
                "}";
        producer.sendMessage(DefaultString.QUENUE_FIRST_TWO,value);
        return value;
    }

}
