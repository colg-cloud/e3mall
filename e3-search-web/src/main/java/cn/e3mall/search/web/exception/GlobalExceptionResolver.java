package cn.e3mall.search.web.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理
 *
 * @author colg
 */
@Slf4j
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (handler instanceof HandlerMethod) {
            /// 解析json
        }
        
        /*
         * colg  [全局异常处理逻辑]
         *  捕获整个系统发生的异常:
         *      1. 异常写入日志文件
         *      2. 及时通知开发人员, 发邮件/短信
         *      3. 展示一个错误页面, 例如: 您的网络故障, 请重试
         */
        
        // 打印控制台
        ex.printStackTrace();
        
        // 写日志
        log.error("系统发生异常: {}", ex.getMessage());
        
        // 发邮件/短信
        
        // 显示错误页面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/exception");
        return modelAndView;
    }

}
