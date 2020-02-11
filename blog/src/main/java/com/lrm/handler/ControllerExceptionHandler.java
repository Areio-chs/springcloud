package com.lrm.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by limi on 2017/10/13.
 */




//拦截所有标志controller注解的控制器!!!!!!!!!!!!!!!!!!




@ControllerAdvice
public class ControllerExceptionHandler {

//获取日志对象
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //方法可以返回带信息的错误页面
    //该注解标识这个方法可以做异常处理的
    @ExceptionHandler(Exception.class)//拦截的异常信息只要是exception级别的都可
    public ModelAndView exceptionHander(HttpServletRequest request, Exception e) throws Exception {
//         记录异常信息 请求的路径        记录异常的信息
        logger.error("Requst URL : {}，Exception : {}", request.getRequestURL(),e);
//当带有一些异常表示状态码时，我们就不要处理它，让boot自己处理，它就会跳到对应的状态码页面了
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {//存在 即不为空时
            throw e;//抛出 让boot处理，去到状态码对应页面 404/500，而不是自定义的error
        }

        ModelAndView mv = new ModelAndView();
//        前端对象可以获取的信息
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception", e);
       //返回到哪个页面
        mv.setViewName("error/error");
        return mv;
    }
}
