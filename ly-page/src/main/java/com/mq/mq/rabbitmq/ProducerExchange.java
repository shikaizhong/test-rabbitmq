package com.mq.mq.rabbitmq;

import com.mq.mq.entity.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//广播消息会每个人都能收得到
public class ProducerExchange {
    private final static String EXCHANGE_NAME = "hello exchange fanout";

    @Autowired
    private static ConnectionUtil connectionUtil;

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取到连接
        Connection connection = connectionUtil.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
        //声明exchange,指定类型为Fanout广播
        //主动声明一个没有附加参数的非自动删除，非持久交换
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        //消息内容
        String message = "hello everyone";

        //发布消息到exchange
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println("[生产者] send" + message);

    }
}
