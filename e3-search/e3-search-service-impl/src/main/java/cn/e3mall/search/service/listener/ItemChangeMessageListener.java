package cn.e3mall.search.service.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import cn.e3mall.search.service.ItemSearchService;
import cn.e3mall.search.service.core.BaseServiceImpl;
import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 监听商品添加消息, 同步商品信息到索引库
 *
 * @author colg
 */
@Slf4j
public class ItemChangeMessageListener extends BaseServiceImpl implements MessageListener {
    
    @Autowired
    private ItemSearchService itemSearchService;

    @Override
    public void onMessage(Message message) {
        // 休眠一秒, 防止事务未提交
        ThreadUtil.sleep(1000);
        try {
            String text = ((TextMessage)message).getText();
            log.info("activemq 接收消息: {}; 同步商品信息到索引库", text);
            long itemId = Long.parseLong(text);
            itemSearchService.addDocument(itemId);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
