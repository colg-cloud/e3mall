package cn.e3mall.common.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * ActiveMq 发布/订阅模式 测试
 *
 * @author colg
 */
@Slf4j
public class ActiveMqTopicTest extends BaseTest {

    /** tcp:// 规定写法; 61616: activemq服务监控端口 */
    private static final String BROKER_URL = "tcp://192.168.21.102:61616";
    /** 消息队列名称 */
    private static final String TOPIC_NAME = "active_mq_topic_test";

    /**
     * 生产者; 发布消息
     *
     * @throws Exception
     */
    @Test
    public void testProducer() throws Exception {
        // 1. 创建一个连接工厂对象, 需要指定服务的ip及端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        // 2. 使用工厂对象来创建一个Connection对象
        Connection connection = connectionFactory.createConnection();
        // 3. 开启连接
        connection.start();
        log.info("connection.getClientID(): {}", connection.getClientID());
        // 4. 创建一个Session对象. 
        // 第一个参数: 是否开启事务(如果消息发布失败, 重发, 一般不开启事务, 如果开启事务, 第二个参数无意义)
        // 第二个参数: 应答模式. 一般自动应答(AUTO_ACKNOWLEDGE)或者手动应答
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        // 5. 创建消息队列. 两种形式: queue/topic; 继承于Destination
        Topic topic = session.createTopic(TOPIC_NAME);

        // 6. 创建消息生产者
        MessageProducer producer = session.createProducer(topic);
        // 7. 创建消息对象, 可以使用文本消息
        TextMessage textMessage = session.createTextMessage("ActiveMq Queue Test: " + DateUtil.now());
        // 8. 发送消息
        producer.send(textMessage);

        // 9. 关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    /**
     * 消费者; 订阅消息
     *
     * @throws Exception
     */
    @Test
    public void testConsumer() throws Exception {
        // 1. 创建一个连接工厂对象, 需要指定服务的ip及端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        // 2. 使用工厂对象来创建一个Connection对象
        Connection connection = connectionFactory.createConnection();
        // 3. 开启连接
        connection.start();
        // 4. 创建一个Session对象. 
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        // 5. 创建消息队列.
        Topic topic = session.createTopic(TOPIC_NAME);
        // 6. 创建消息消费者
        MessageConsumer consumer = session.createConsumer(topic);
        // 7. 接收消息
        consumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {
                // 获取消息内容
                try {
                    String result = ((TextMessage)message).getText();
                    log.info("发布/订阅模式接收到的消息: {}", result);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        // 启动多次: 发布/订阅模式, 即一个生产者产生消息并进行发送后, 可以由多个消费者进行接收
        log.info("{} 已经启动: {}", topic.getTopicName(), RandomUtil.randomInt(1, 100));
        // 8. 等待键盘输入; 否则一直监听着
        System.in.read();
        // 9. 关闭资源
        consumer.close();
        session.close();
        connection.close();
    }

}
