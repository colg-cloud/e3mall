package cn.e3mal.item.listener;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Value;

import cn.e3mal.item.core.BaseController;
import cn.e3mal.item.vo.TbItemDto;
import cn.e3mall.manager.pojo.TbItem;
import cn.e3mall.manager.pojo.TbItemDesc;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 监听商品添加消息,生成对应的静态页面
 *
 * @author colg
 */
public class HtmlGenListener extends BaseController implements MessageListener {

	@Value("${HTML_GEN_PATH}")
	private String htmlGenPath;

	@Override
	public void onMessage(Message message) {

		try {
			// 创建一个模版
			// 从消息中取商品id
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			Long itemId = Long.parseLong(text);
			
			// 等待事务提交
			Thread.sleep(1000);
			
			// 根据商品id查询商品信息,商品基本信息和商品描述
			TbItem tbItem = tbItemService.getTbItemById(itemId);
			TbItemDto tbItemDto = new TbItemDto(tbItem);
			TbItemDesc tbItemDesc = tbItemDescService.findById(itemId);
			// 创建一个数据集,把商品数据封装
			Map<String, Object> map = new HashMap<>(2);
			map.put("item", tbItemDto);
			map.put("itemDesc", tbItemDesc);
			// 加载模版对象
			Configuration configuration = freeMarkerConfig.getConfiguration();
			Template template = configuration.getTemplate("item.ftl");
			// 创建一个输出流,指定输出的目录及文件名
			Writer out = new FileWriter(htmlGenPath + itemId + ".html");
			// 生成静态页面
			template.process(map, out);
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
