package cn.e3mall.sso;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.e3mall.manager.pojo.TbUser;
import cn.e3mall.sso.service.TbUserService;

/**
 * 
 *
 * @author colg
 */
public class MainTest {

	private ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");

	@Test
	public void test1() {
		TbUserService tbUserService = applicationContext.getBean(TbUserService.class);
		TbUser tbUser = tbUserService.findById((long) 1);
		System.out.println(tbUser);
	}
}
