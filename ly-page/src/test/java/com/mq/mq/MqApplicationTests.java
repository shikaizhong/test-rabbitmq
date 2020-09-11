package com.mq.mq;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
//@SpringBootTest(classes = MqApplication.class)
class MqApplicationTests {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void testSend() throws InterruptedException {
        String msg = "hello,spring boot amqp";
        amqpTemplate.convertAndSend("spring.test.exchange", "a.b", msg);
        //等待
        Thread.sleep(100000);
    }

}
