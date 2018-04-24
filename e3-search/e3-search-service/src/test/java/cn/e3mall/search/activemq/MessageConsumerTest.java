package cn.e3mall.search.activemq;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 *
 * @author colg
 */
public class MessageConsumerTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumerTest.class);

	private String[] configLocations = { "classpath:spring/applicationContext-dao.xml", "classpath:spring/applicationContext-activemq.xml" };

	/** 初始化spring容器 */
	@SuppressWarnings("unused")
	private ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configLocations);

	/**
	 * 接收消息，监听
	 */
	@Test
	public void msgConsumer() throws IOException {
		LOGGER.info("msgConsumer 启动");
		// 等待
		System.in.read();
	}
}
