package com.controller;

import com.domain.Message;
import com.service.SerialPortService;
import com.service.impl.SerialPortServiceImpl;
import gnu.io.NoSuchPortException;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/serialPort")
public class SerialPortController {

    SerialPortService serialPortService = new SerialPortServiceImpl();

    public SerialPortController() throws NoSuchPortException {
    }

    @RequestMapping("/open")
    public void open(){
        serialPortService.open();
    }

    @RequestMapping("/testVoltage")
    public @ResponseBody Map<String, List> testVoltage(){
        Map<String, List> map = new HashMap<String, List>();
        Map<String, Double> DataMap = new HashMap<String, Double>();
        while (true){
            serialPortService.send("0105");
            Message v = serialPortService.getV();
            DataMap.put(v.getTime()+(int)(Math.random()*10), v.getMessage()+(Math.random()*10));
            if (DataMap.size()>=5){
                break;
            }
        }
        DataMap = sortMapByKey1(DataMap, true);
        //System.out.println(DataMap);
        ArrayList<String> xList = new ArrayList<>();
        ArrayList<Double> yList = new ArrayList<>();
        //遍历数据，封装到新的map中
        Set<String> keySet = DataMap.keySet();
        for (String key : keySet) {
            xList.add(key);
            yList.add(DataMap.get(key));
        }
        map.put("xList", xList);
        map.put("yList",yList);
        return map;
    }

    @RequestMapping("/testTemperature")
    public @ResponseBody Map<String, List> testTemperature(){
        Map<String, List> map = new HashMap<String, List>();
        Map<String, Double> DataMap = new HashMap<String, Double>();
        while (true){
            serialPortService.send("0101");
            Message v = serialPortService.getV();
            DataMap.put(v.getTime()+(int)(Math.random()*10), v.getMessage()+(Math.random()*10));
            if (DataMap.size()>=5){
                break;
            }
        }
        DataMap = sortMapByKey1(DataMap, true);
        //System.out.println(DataMap);
        ArrayList<String> xList = new ArrayList<>();
        ArrayList<Double> yList = new ArrayList<>();
        //遍历数据，封装到新的map中
        Set<String> keySet = DataMap.keySet();
        for (String key : keySet) {
            xList.add(key);
            yList.add(DataMap.get(key));
        }
        map.put("xList", xList);
        map.put("yList",yList);
        return map;
    }

    //将map按照key进行排序
    public static Map<String, Double> sortMapByKey1(Map<String, Double> oriMap, final boolean isRise) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<String, Double> sortMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (isRise) {
                    // 升序排序
                    return o1.compareTo(o2);
                } else {
                    // 降序排序
                    return o2.compareTo(o1);
                }
            }
        });
        sortMap.putAll(oriMap);
        return sortMap;
    }

    @RequestMapping("/send")//取得请求参数order赋给Integer order，得到要发的指令
    @ResponseBody
    public  String send(@RequestParam(value = "order") Integer order){
        System.out.println("用于发送指令");
        System.out.println(order);
        if(order==1)
        {
            System.out.println("只开灯一");
            serialPortService.send("010f01");
            //调用方法把指令写给串口
            //这里好像不需要return？因为页面不需要得到什么东西
            return "1";
        }
        else if (order==2)
        {
            System.out.println("只开灯二");
            serialPortService.send("010f02");
            return "2";
        }else if(order==3){
            System.out.println("同时开两个灯");
            serialPortService.send("010f03");
            return "3";
        }
        else{
            System.out.println("关闭所有灯");
            serialPortService.send("010f");
            return "4";
        }
    }

    @Test
    public void test(){
        Map<String, Integer> DataMap = new HashMap<String, Integer>();
        for (int i = 0; i < 6; i++) {
            System.out.println(i);
            DataMap.put("2018-12-19",i);
        }
        /*for (int i = 0; i < 5; i++) {
            System.out.println(i);
            Message v = serialPortService.getV();
            DataMap.put(v.getTime(),v.getMessage());
        }*/
        System.out.println(DataMap);

    }
}
