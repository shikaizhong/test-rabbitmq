package com.mq.mq.rabbitmq;

import com.mq.mq.entity.ConnectionUtil;
import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveTopic {
    private final static String EXCHANGE_NAME = "hello exchange topic";
    private final static String QUEUE_NAME = "hello exchange queue topic";
    @Autowired
    private static ConnectionUtil connectionUtil;

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取到连接
        Connection connection = connectionUtil.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
        //声明队列
//        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //队列持久化
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //绑定队列到交换机,同时指定需要订阅的routing key,假设此处需要 play和swim 消息
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "eat.apple");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "eat.banana");
        //定义队列的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 获取消息，并且处理，这个方法类似事件监听，如果有消息的时候，会被自动调用
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                // body 即消息体
                String msg = new String(body);
                System.out.println(" [用户] received : " + msg + "!");
            }
        };
        // 监听队列，第二个参数：是否自动进行消息确认。
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
