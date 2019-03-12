package cn.e3mall.search.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Dubbo 启动类
 *
 * @author colg
 */
@Slf4j
public class E3SearchServiceApplication {

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        applicationContext.start();
        
        log.info("Dubbo Publish [e3-search-service: {}]", DateUtil.now());
        
        synchronized (E3SearchServiceApplication.class) {
            E3SearchServiceApplication.class.wait();
            applicationContext.close();
        } 
    }
}
