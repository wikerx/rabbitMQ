package com.scott.rabbit.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import com.scott.rabbit.utils.DefaultString;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MainTest {
    private static Log log = LogFactory.getLog(MainTest.class);

    public static void main(String[] args) throws Exception{
//        生产消息
//        sendMessage(DefaultString.QUENUE_TEST, "巴拉巴拉小魔仙，变身！！！");
//        productMoreMessage(DefaultString.QUENUE_TEST, "巴拉巴拉小魔仙，变身！！！");
//        消费消息
//        consumeMessage(DefaultString.QUENUE_TEST);
//        consumeMoreMessage(DefaultString.QUENUE_TEST);
//        consumeMoreMessageBasic(DefaultString.QUENUE_TEST);

//        订阅消息
        product1(DefaultString.EXCHANGE_NAME,"巴拉巴拉小魔仙，变身！！！");
        consume1(DefaultString.QUEUE_NAME1);
        consume2(DefaultString.QUEUE_NAME2);

//        路由模式
        routeProduct(DefaultString.QUEUE_EXCHANGE_DIRECT, "巴拉巴拉小魔仙，变身！！！");
        routeConsume1(DefaultString.QUEUE_QUEUE_EXCHANGE_DIRECT1);
        routeConsume2(DefaultString.QUEUE_QUEUE_EXCHANGE_DIRECT2);

//        通配符模式
        unusualProduct(DefaultString.QUEUE_EXCHANGE_TOPIC, "巴拉巴拉小魔仙，变身！！！");
        unusualConsume1(DefaultString.QUEUE_QUEUE_EXCHANGE_TOPIC1);
        unusualConsume2(DefaultString.QUEUE_QUEUE_EXCHANGE_TOPIC2);


    }

//    创建队列，发送消息：生产者
    public static void sendMessage(String queueName, String message) throws Exception{
//        获取数据连接
        Connection connection = MQConnection.getConnection();
//        创建通信通道
        Channel channel = connection.createChannel();
//        申明创建队列
        channel.queueDeclare(queueName,false,false,false,null);
        channel.basicPublish("",queueName,null,message.getBytes());
        log.info("发送消息：" + message);
//        关闭连接和通道
        MQConnection.close(connection,channel);
    }

//    创建队列，消费消息：消费者
    public static void consumeMessage(String queueName) throws Exception{
//        获取数据连接
        Connection connection = MQConnection.getConnection();
//        创建通信通道
        Channel channel = connection.createChannel();
//        申明创建队列
        channel.queueDeclare(queueName,false,false,false,null);
//        同一时刻服务器只发送一条消息给消费端
        channel.basicQos(1);
//        创建消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
//        监听队列   第二个参数：true 自动ack
        channel.basicConsume(queueName,true,consumer);
//        开始消费
        while(true){
            //这个方法会阻塞住，直到获取到消息
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            log.info("接收到消息："+message);
        }

    }

//    连续生产消息
    public static void productMoreMessage(String queueName, String message) throws Exception{
//        获取数据连接
        Connection connection = MQConnection.getConnection();
//        创建通信通道
        Channel channel = connection.createChannel();
//        申明创建队列
        channel.queueDeclare(queueName,false,false,false,null);
        for (int i = 0; i < 101; i++) {
            channel.basicPublish("",queueName,null,message.getBytes());
            log.info("发送消息：" + message);
        }
//        关闭连接和通道
        MQConnection.close(connection,channel);
    }

//    批量消费消息：手动消费
    public static void consumeMoreMessage(String queueName) throws Exception{
//        获取数据连接
        Connection connection = MQConnection.getConnection();
//        创建通信通道
        Channel channel = connection.createChannel();
//        申明创建队列
        channel.queueDeclare(queueName,false,false,false,null);
//        同一时刻服务器只发送10条消息给消费端
        channel.basicQos(10);
//        创建消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
//        监听队列  第二个参数：false 手动ack
        channel.basicConsume(queueName,false,consumer);
//        开始消费
        while(true){
//            这个方法会阻塞住，直到获取到消息
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            log.info("接收到消息："+message);
//            休眠100ms
            Thread.sleep(100);
//            消息消费完给服务器返回确认状态，表示该消息已被消费  手动专属
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        }

    }

//    批量消费消息：自动消费
    public static void consumeMoreMessageBasic(String queueName) throws Exception{
//        获取数据连接
        Connection connection = MQConnection.getConnection();
//        创建通信通道
        Channel channel = connection.createChannel();
//        申明创建队列
        channel.queueDeclare(queueName,false,false,false,null);
//        prefetchSize：消息的大小
//    　　prefetchCount：会告诉RabbitMQ不要同时给一个消费者推送多于N个消息，即一旦有N个消息还没有ack，则该consumer将block掉，直到有消息ack
//    　　global：是否将上面设置应用于channel，简单点说，就是上面限制是channel级别的还是consumer级别
        channel.basicQos(100);
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true,consumer);
        while(true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            log.info("接收到消息："+message);
            Thread.sleep(100);
        }
    }



    /**
     * 订阅模式
     * */
//    生产者
    public static void product1(String queueName, String message) throws Exception{
        Connection connection = MQConnection.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机 fanout：交换机类型 主要有fanout,direct,topics三种
        channel.exchangeDeclare(queueName,"fanout");
        channel.basicPublish(queueName,"",null,message.getBytes());
        log.info(message);
        MQConnection.close(connection,channel);
    }

//    消费者
    public static void consume1(String queueName) throws Exception{
        Connection connection = MQConnection.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queueName,false,false,false,null);
        //绑定队列到交换机上
        channel.queueBind(queueName,DefaultString.EXCHANGE_NAME,"");
        channel.basicQos(1);
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName,true,consumer);
        while(true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            log.info("消费消息：" + message);
        }
    }

//    消费者
    public static void consume2(String queueName) throws Exception{
        Connection connection = MQConnection.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queueName,false,false,false,null);
        //绑定队列到交换机上
        channel.queueBind(queueName,DefaultString.EXCHANGE_NAME,"");
        channel.basicQos(1);
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName,true,consumer);

        while(true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            log.info("消费消息：" + message);
        }
    }


    /**
     * 路由模式
     * */
//    生产者
    public static void routeProduct(String queueName, String message)throws Exception{
        Connection connection = MQConnection.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机 fanout：交换机类型 主要有fanout,direct,topics三种
        channel.exchangeDeclare(queueName,"direct");
        channel.basicPublish(queueName,"dog",null,message.getBytes());
        System.out.println(message);
        channel.close();
        connection.close();
    }

//    消费者
    public static void routeConsume1(String queueName) throws Exception{
        Connection connection = MQConnection.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queueName,false,false,false,null);
        //绑定队列到交换机上,并制定路由键为"dog"
        channel.queueBind(queueName, DefaultString.EXCHANGE_NAME,"dog");
        channel.basicQos(1);
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName,true,consumer);
        while(true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(message);
        }
    }

//    消费者
    public static void routeConsume2(String queueName) throws Exception{
        Connection connection = MQConnection.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queueName,false,false,false,null);
        //绑定队列到交换机上,并制定路由键为"cat"
        channel.queueBind(queueName, DefaultString.EXCHANGE_NAME,"cat");
        channel.basicQos(1);
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName,true,consumer);
        while(true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(message);
        }
    }



    /**
     * 通配符模式
     * */
//    生产者
    public static void unusualProduct(String queueName, String message) throws Exception{
        Connection connection = MQConnection.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机 topic：交换机类型
        channel.exchangeDeclare(queueName,"topic");
        channel.basicPublish(queueName,"dog.1",null,message.getBytes());
        System.out.println(message);
        MQConnection.close(connection,channel);
    }

//    消费者
    public static void unusualConsume1(String queueName) throws Exception{
        Connection connection = MQConnection.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queueName,false,false,false,null);
        //绑定队列到交换机上,并制定路由键匹配规则为"dog.*"
        channel.queueBind(queueName, DefaultString.EXCHANGE_NAME,"dog.*");
        channel.basicQos(1);
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName,true,consumer);

        while(true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(message);
        }
    }


//    消费者
    public static void unusualConsume2(String queueName) throws Exception{
        Connection connection = MQConnection.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queueName,false,false,false,null);
        //绑定队列到交换机上,并制定路由键匹配规则为"#.1"
        channel.queueBind(queueName, DefaultString.EXCHANGE_NAME,"#.1");
        channel.basicQos(1);
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName,true,consumer);

        while(true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(message);
        }
    }



}
