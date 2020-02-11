package com.lrm;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by limi on 2017/10/13.
 */
//指定返回的状态码，springboot拿到这个状态去到对应的页面
@ResponseStatus(HttpStatus.NOT_FOUND)
//继承runtime才可以捕获
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
    }
//构造方法的重载
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
