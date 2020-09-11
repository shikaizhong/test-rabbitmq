package com.mq.mq.rabbitmq;

import com.mq.mq.entity.ConnectionUtil;
import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

//消费者获取消息
public class Receive {
    private final static String QUEUE_NAME = "hello";
    @Autowired
    private static ConnectionUtil connectionUtil;

    public static void main(String[] argv) throws Exception {
        // 获取到连接
        Connection connection = connectionUtil.getConnection();
        // 创建通道
        final Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //设置每个消费者同时只能处理一条信息
        channel.basicQos(1);
        // 定义队列的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 获取消息，并且处理，这个方法类似事件监听，如果有消息的时候，会被自动调用
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                // body 即消息体
                String msg = new String(body);
                System.out.println(" [用户] received : " + msg + "!");
                try {
                    //模拟完成任务的耗时
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                //手动ack
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        // 监听队列，第二个参数：是否自动进行消息确认。
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}
