package com.mq.mq.entity;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtil {
    /**
     * 建立与rabbitmq的连接
     */
    public static Connection getConnection() throws IOException, TimeoutException {
        //创建连接
        ConnectionFactory factory = new ConnectionFactory();
        //设置rabbitmq的主机名称
        factory.setHost("127.0.0.1");
        //设置连接的用户名
        factory.setUsername("simon");
        //设置连接的用户密码
        factory.setPassword("123456");
        //设置端口
        factory.setPort(5672);
        //创建一个链接
        Connection connection = factory.newConnection();
        return connection;
    }
}
