package com.lrm.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created by limi on 2017/10/13.
 */
@Aspect
@Component
public class LogAspect {
//拿到日志记录器
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//注解声明它是一个切面  拦截哪些类 web下所有的类的任何方法
    @Pointcut("execution(* com.lrm.web.*.*(..))")
    public void log() {}

//在切面之前执行一些东西，传递我的切面作为参数
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        //        logger.info("--------doBefore--------");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
//        拿到这个请求
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
//        获得拦截的类名和方法名
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
//      获得请求参数
        Object[] args = joinPoint.getArgs();
//        RequestLog是下面定义的类
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        logger.info("Request : {}", requestLog);
    }

    @After("log()")
    public void doAfter() {
//        logger.info("--------doAfter--------");
    }
//俘获拦截的方法所返回的内容
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterRuturn(Object result) {
        logger.info("Result : {}", result);
    }


    //封装成一个类
    private class RequestLog {
        private String url;
        private String ip;//访问者ip
        private String classMethod;//调用哪个类的哪个方法
        private Object[] args;//请求参数 用数组

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}
