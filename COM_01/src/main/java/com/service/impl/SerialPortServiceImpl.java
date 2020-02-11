package com.service.impl;

import com.dao.CRC16M;
import com.dao.SerialPortDao;
import com.dao.intToHex_Alter;
import com.domain.Message;
import com.service.SerialPortService;
import org.junit.Test;

import java.io.*;

public class SerialPortServiceImpl implements SerialPortService {

    SerialPortDao serialPortDao = new SerialPortDao();

    @Override
    public boolean open(){

        System.out.println(serialPortDao.openSerialPort("COM8"));
        serialPortDao.run();
        return true;
    }

   /* @Test
    public void test(){
        byte[] sbuf = CRC16M.getSendBuf("0105");
        String s = CRC16M.getBufHexStr(sbuf);
        System.out.println(s);
        int a = Integer.valueOf(s);
        System.out.println(a);
        String s1 = intToHex_Alter.intToHex(Integer.valueOf(s));
        System.out.println(s1);
    }*/
    @Override
    public boolean send(String num) {
        byte[] sbuf = CRC16M.getSendBuf(num);
        String s = CRC16M.getBufHexStr(sbuf);
        //String s1 = s+"0D0A";
        System.out.println(s);
        //String s1 = intToHex_Alter.intToHex(Integer.valueOf(s));
        serialPortDao.sendMessage(s.getBytes());
        serialPortDao.run();
        return true;
    }

    @Override
    public Message getV() {
        Message v = new Message();
        try {
           // BufferedReader br = new BufferedReader(new FileReader("token.txt"));
            BufferedReader br = new BufferedReader(new FileReader("D:\\2.txt"));
            String line = br.readLine();
            String[] str1 = line.split(",");
            if (str1.length >= 2) {
                v.setMessage(Double.valueOf(str1[0]));
                v.setTime(str1[1].substring(0,19));
            }
            br.close();//关闭文件
        } catch (IOException e) {
            e.printStackTrace();
        }
        return v;
    }

}
