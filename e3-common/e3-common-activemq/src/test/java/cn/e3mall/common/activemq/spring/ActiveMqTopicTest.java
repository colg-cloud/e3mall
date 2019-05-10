package cn.e3mall.common.activemq.spring;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQTopic;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * ActiveMq 发布/订阅模式 测试
 *
 * @author colg
 */
@Slf4j
public class ActiveMqTopicTest {

    /** 初始化Spring容器 */
    private ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/activemq-topic.xml");
    private JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
    private Destination destination = applicationContext.getBean(ActiveMQTopic.class);

    /**
     * 生产者; 发送消息
     *
     * @throws Exception
     */
    @Test
    public void testProducre() {
        jmsTemplate.send(destination, new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("ActiveMq Queue Spring Test: " + DateUtil.now());
            }
        });
    }

    /**
     * 消费者; 接收消息
     *
     * @throws Exception
     */
    @Test
    public void testConsumer() throws Exception {
        Message message = jmsTemplate.receive(destination);
        String result = ((TextMessage)message).getText();
        log.info("发布/订阅模式接收到的消息: {}", result);
    }
}
