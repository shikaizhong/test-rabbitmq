package com.mq.mq.rabbitmq;

import com.mq.mq.entity.ConnectionUtil;
import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveB {
    private final static String QUEUE_NAME = "hello";
    @Autowired
    private static ConnectionUtil connectionUtil;

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取到连接
        Connection connection = connectionUtil.getConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //定义队列的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            //获取消息,并且处理,这个方法类似事件监听,如果有消息,会被自动调用
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                //body是消息体
                String msg = new String(body);
                System.out.println("[用户]接收" + msg);
                //收到进行ack
                //Acknowledge one or several received
                /*
                参数1: deliveryTag从收到的{@link com.rabbitmq.client.AMQP.Basic.GetOk}或{@link com.rabbitmq.client.AMQP.Basic.Deliver}中标记
                参数2: 多个true表示确认直到和之前的所有消息
                 */
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        //监听队列,第二个参数false,手动进行ack
        //如果消息不太重要，丢失也没有影响，那么自动ACK会比较方便
        //如果消息非常重要，不容丢失。那么最好在消费完成后手动ACK，否则接收消息后就自动ACK，RabbitMQ就会把消息从队列中删除。如果此时消费者宕机，那么消息就丢失了。
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}
