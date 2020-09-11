package com.mq.mq.rabbitmq;

import com.mq.mq.entity.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerTopic {
    private final static String EXCHANGE_NAME = "hello exchange topic";

    @Autowired
    private static ConnectionUtil connectionUtil;

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取到连接
        Connection connection = connectionUtil.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
        //声明exchange,指定类型为direct
        //主动声明一个没有附加参数的非自动删除，非持久交换
//        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        //交换机持久化(避免消息丢失)
        channel.exchangeDeclare(EXCHANGE_NAME, "topic", true);
        //消息内容
        String message = "你妈妈喊你写作业了";
        //发送消息,并且指定routing key为 eat ,代表吃
        //`eat.#`：能够匹配`eat.spu.eat` 或者 `eat.eat`
        //`eat.*`：只能匹配`eat.eat`
//        channel.basicPublish(EXCHANGE_NAME, "eat.rice", null, message.getBytes());
//        channel.basicPublish(EXCHANGE_NAME, "peat.rice.black", null, message.getBytes());
        //消息持久化
        channel.basicPublish(EXCHANGE_NAME, "peat.rice.black", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
//        channel.basicPublish(EXCHANGE_NAME, "swim river", null, message.getBytes());
        System.out.println("小明说" + message);
    }
}
