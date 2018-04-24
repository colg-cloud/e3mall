package cn.e3mll.order;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * e3-order 服务启动
 *
 * @author colg
 */
public class PublishOrder {

	private static final Logger LOGGER = LoggerFactory.getLogger(PublishOrder.class);

	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		context.start();
		LOGGER.info("Dubbo Publishing OrderService - {}", "http://127.0.0.1:8091/");
		System.in.read();
		context.close();
	}
}
