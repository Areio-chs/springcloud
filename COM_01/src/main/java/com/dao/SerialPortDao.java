package com.dao;

import gnu.io.*;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.TooManyListenersException;


public class SerialPortDao implements Runnable, SerialPortEventListener {

    private static boolean isOpen=false;
    static Set<CommPortIdentifier> portList=new HashSet<CommPortIdentifier>();
    final static String appName="MyApp";
    private static InputStream is;
    private static OutputStream os;
    private static SerialPort serialPort;
    byte[] cache = new byte[1024];  //定义用于缓存读入数据的数组

    public boolean openSerialPort(String serialPortName){
        try {
            CommPortIdentifier portIp = CommPortIdentifier.getPortIdentifier(serialPortName);
            serialPort=(SerialPort) portIp.open(appName, 2222);
            /* open方法打开通讯端口，获得一个CommPort对象。它使程序独占端口。
             * 如果端口正被其他应用程序占用，将使用 CommPortOwnershipListener事件机制，传递一个PORT_OWNERSHIP_REQUESTED事件。
             * 每个端口都关联一个 InputStream 和一个OutputStream。
             * 如果端口是用open方法打开的，那么任何的getInputStream都将返回相同的数据流对象，除非有close 被调用。
             * 有两个参数，第一个为应用程序名；第二个参数是在端口打开时阻塞等待的毫秒数。
             */
        } catch (Exception e) {
            return false;
        }
        try {
            is=serialPort.getInputStream();/*获取端口的输入流对象*/
            os=serialPort.getOutputStream();/*获取端口的输出流对象*/
        } catch (IOException e) {
            return false;
        }
        try {
            serialPort.addEventListener(this);/*注册一个SerialPortEventListener事件来监听串口事件*/
        } catch (TooManyListenersException e) {
            return false;
        }
        serialPort.notifyOnDataAvailable(true);/*数据可用*/
        try {
            /*设置串口初始化参数，依次是波特率，数据位，停止位和校验*/
            serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1 , SerialPort.PARITY_NONE);
        } catch (UnsupportedCommOperationException e) {
            return false;
        }
        return true;
    }

    public boolean closeSerialPort(){
        if(isOpen){
            try {
                is.close();
                os.close();
                serialPort.notifyOnDataAvailable(false);
                serialPort.removeEventListener();
                serialPort.close();
                isOpen = false;
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    public boolean sendMessage(byte[] message){
        try {
                os.write(message);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    //重写继承的监听器方法
    @Override
    public void serialEvent(SerialPortEvent event) {

        //记录已经到达串口COM21且未被读取的数据的字节（Byte）数。
        int availableBytes = 0;

        //如果是数据可用的时间发送，则进行数据的读写
        if(event.getEventType() == SerialPortEvent.DATA_AVAILABLE){
            try {
                availableBytes = is.available();
                while(availableBytes > 0){
                    is.read(cache);
                    System.out.print("收到的：");
                    for(int i = 0; i < cache.length && i < availableBytes; i++){
                        //解码并输出数据
                        System.out.print((char)cache[i]);
                    }
                    availableBytes = is.available();
                    SPCommandDao.startDoMessage(new String (cache));//这一句是我的自定义类，处理接受到的信息，可删除
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(50);//每次收发数据完毕后线程暂停50ms
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
