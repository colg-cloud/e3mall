package cn.e3mall.manager.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Dubbo 启动类
 *
 * @author colg
 */
@Slf4j
public class E3ManagreServiceApplication {
    
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        applicationContext.start();
        
        log.info("Dubbo Publish [e3-manager-service: {}]", DateUtil.now());
        
        synchronized (E3ManagreServiceApplication.class) {
            E3ManagreServiceApplication.class.wait();
            applicationContext.close();
        }
    }
}
