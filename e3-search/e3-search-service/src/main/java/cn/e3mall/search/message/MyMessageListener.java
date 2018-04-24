package cn.e3mall.search.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息监听
 *
 * @author colg
 */
public class MyMessageListener implements MessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyMessageListener.class);

	@Override
	public void onMessage(Message message) {
		// 取消息内容
		TextMessage textMessage = (TextMessage) message;
		String text = null;
		try {
			text = textMessage.getText();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		LOGGER.info("接收到的消息是：{}", text);
	}

}
