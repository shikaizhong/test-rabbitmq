package com.mq.mq.rabbitmq;

import com.mq.mq.comment.MyPropertiesConfig;
import com.mq.mq.entity.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//使用新建的用户simon
public class Producer {

    private final static String QUEUE_NAME = "hello";
    @Autowired
    private static ConnectionUtil connectionUtil;

    public static void main(String[] args) throws IOException, TimeoutException {

        //获取连接
        Connection connection = connectionUtil.getConnection();
        //创建一个通道
        Channel channel = connection.createChannel();
        //指定一个队列,不存在的话自动创建这个队列
        /*
        参数1:队列名称
        参数2:如果我们声明一个持久队列，则为true（该队列将在服务器重启后继续存在）
        参数3:Exclusive如果我们声明一个独占队列，则为true（仅限此连接）
        参数4:如果我们声明一个自动删除队列，则为true（服务器将在不再使用它时将其删除）
        参数5:队列的其他属性（构造参数）
        参数6:一个声明确认方法，指示队列已成功声明
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //发送消息
        String message = "hello simon";
        /**
         * 发布消息
         *参数1: 交换交流以将消息发布到
         * 参数2: routingKey路由密钥
         * 参数3: 支持消息的其他属性-路由标头等
         * 参数4: 正文消息正文
         */
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("发送" + message);
    }
}
