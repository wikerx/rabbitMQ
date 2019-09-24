package com.scott.rabbit.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 创建MQ连接
 * */
public class MQConnection {

//    创建连接工厂，返回可使用的连接
    public static Connection getConnection() throws Exception{
//        定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("/");
        Connection connection = factory.newConnection();
        return connection;
    }

//    关闭connection和channel
    public static void close(Connection con, Channel channel) throws Exception{
        if(channel.isOpen()){
            channel.close();
        }
        if(con.isOpen()){
            con.close();
        }
    }


}
