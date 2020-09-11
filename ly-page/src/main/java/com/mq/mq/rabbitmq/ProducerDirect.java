package com.mq.mq.rabbitmq;

import com.mq.mq.entity.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerDirect {
    private final static String EXCHANGE_NAME = "hello exchange direct";

    @Autowired
    private static ConnectionUtil connectionUtil;

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取到连接
        Connection connection = connectionUtil.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
        //声明exchange,指定类型为direct
        //主动声明一个没有附加参数的非自动删除，非持久交换
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        //消息内容
        String message = "你妈妈喊你吃饭了";
        //发送消息,并且指定routing key为 eat ,代表吃
        channel.basicPublish(EXCHANGE_NAME, "eat", null, message.getBytes());
        channel.basicPublish(EXCHANGE_NAME, "play", null, message.getBytes());
        channel.basicPublish(EXCHANGE_NAME, "swim", null, message.getBytes());
        System.out.println("小明说" + message);
    }
}
