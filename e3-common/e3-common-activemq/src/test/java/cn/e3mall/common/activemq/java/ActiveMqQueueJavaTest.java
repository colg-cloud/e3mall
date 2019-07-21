package cn.e3mall.common.activemq.java;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import cn.e3mall.common.activemq.BaseTest;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ObjectUtil;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * ActiveMq 队列模式 测试
 *
 * @author colg
 */
@Slf4j
public class ActiveMqQueueJavaTest extends BaseTest {

    /**
     * tcp:// 规定写法; 61616: activemq服务监控端口
     */
    private static final String BROKER_URL = "tcp://192.168.21.102:61616";

    /**
     * 消息队列名称
     */
    private static final String QUEUE_NAME = "cn.e3mall.common.activemq.java.ActiveMqQueueJavaTest";

    /**
     * 生产者; 发送消息
     *
     * @throws JMSException
     */
    @Test
    public void testProducer() throws JMSException {
        // 1. 创建一个连接工厂对象, 需要指定服务的ip及端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        // 2. 使用工厂对象来创建一个Connection对象
        Connection connection = connectionFactory.createConnection();
        // 3. 开启连接
        connection.start();

        // 4. 创建一个Session对象. 
        // 第一个参数: 是否开启事务(如果消息发布失败, 重发, 一般不开启事务, 如果开启事务, 第二个参数无意义)
        // 第二个参数: 应答模式. 一般自动应答(AUTO_ACKNOWLEDGE)或者手动应答
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 5. 创建消息队列. 两种形式: queue/topic; 继承于Destination
        Queue queue = session.createQueue(QUEUE_NAME);
        // 6. 创建消息生产者
        MessageProducer messageProducer = session.createProducer(queue);
        // 7. 创建消息对象, 可以使用文本消息
        TextMessage textMessage = session.createTextMessage();
        for (int i = 1; i <= 3; i++) {
            textMessage.setText("cn.e3mall.common.activemq.java.ActiveMqQueueJavaTest.testProducer -> " + i + ": " + DateUtil.now());
            // 8. 发送消息
            messageProducer.send(textMessage);
            ThreadUtil.sleep(1000);
        }

        // 9. 关闭资源
        messageProducer.close();
        session.close();
        connection.close();

        log.info("队列模式 -> 生产者发布消息到ActiveMQ完成: {}", DateUtil.now());
    }

    /**
     * 消费者; 接收消息
     *
     * @throws JMSException
     * @throws IOException
     */
    @Test
    public void testConsumer() throws JMSException, IOException {
        // 1. 创建一个连接工厂对象, 需要指定服务的ip及端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        // 2. 使用工厂对象来创建一个Connection对象
        Connection connection = connectionFactory.createConnection();
        // 3. 开启连接
        connection.start();

        // 4. 创建一个Session对象. 
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 5. 创建消息队列.
        Queue queue = session.createQueue(QUEUE_NAME);
        // 6. 创建消息消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);

        // 7. 接收消息
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof ActiveMQTextMessage) {
                    ActiveMQTextMessage textMessage = (ActiveMQTextMessage)message;
                    try {
                        String result = ((TextMessage)message).getText();
                        log.info("队列模式 -> 消费者接收到的消息: {}", result);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // 启动多次; 消息队列模式: 点对点的, 即一个生产者和一个消费者一一对应
        log.info("{} 已经启动: {}", queue.getQueueName(), RandomUtil.randomInt(1, 10));
        // 8. 等待键盘输入; 否则一直监听着

        System.in.read();
        // 9. 关闭资源
        messageConsumer.close();
        session.close();
        connection.close();
    }
}
