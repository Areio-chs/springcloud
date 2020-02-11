package com.service;

import com.domain.Message;

public interface SerialPortService {

    //打开端口
    public boolean open();

    //打开端口
    public boolean send(String num);


    public Message getV();
}
