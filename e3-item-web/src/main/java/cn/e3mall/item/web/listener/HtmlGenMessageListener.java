package cn.e3mall.item.web.listener;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Value;

import cn.e3mall.common.base.entity.Item;
import cn.e3mall.common.base.entity.ItemDesc;
import cn.e3mall.item.web.core.BaseController;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.thread.ThreadUtil;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

/**
 * 监听商品添加消息, 生成对应的静态页面
 *
 * @author colg
 */
@Slf4j
public class HtmlGenMessageListener extends BaseController implements MessageListener {
    
    /** 静态页面输出目录: D:/workspace-all/iheima/e3mall/e3-doc/freemarker/item/ */
    @Value("${html_gen_dir}")
    private String htmlGenDir;

    @Override
    public void onMessage(Message message) {
        // 休眠一秒, 防止事务未提交
        ThreadUtil.sleep(1000);
        try {
            String text = ((TextMessage)message).getText();
            log.info("activemq 接收消息: {}; 生成html静态页面", text);
            long itemId = Long.parseLong(text);
            // 获取商品信息
            Item item = itemService.getTbItemById(itemId);
            // 获取商品描述
            ItemDesc itemDesc = itemDescService.findById(itemId);
            
            // 封装数据集
            Dict data = Dict.create().set("item", item)
                                     .set("itemDesc", itemDesc);
            // 指定文件输出路径
            String filepath = htmlGenDir + itemId + ".html";
            Writer out = new FileWriter(filepath);
            // 加载模版文件, 生成静态文件
            freeMarkerConfig.getConfiguration().getTemplate("index.ftl").process(data, out);
            log.info("输出路径: {}", filepath);
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

}
