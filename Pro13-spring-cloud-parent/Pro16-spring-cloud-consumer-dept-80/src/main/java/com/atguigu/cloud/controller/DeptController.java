package com.atguigu.cloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.atguigu.cloud.entities.Dept;

@RestController
public class DeptController {

     //private static final String REST_URL_PREFIX = "http://localhost:8001";
    //用微服务的名称，是provider配置文件里写的名字
    private static final String REST_URL_PREFIX = "http://ATGUIGU-SCMS-DEPT";

    //springcloud是基于http请求，下面这个是http的各种请求都有
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/register/consummer/dept/add")
    public boolean add(Dept dept) {       //请求地址                      //请求参数  // 返回类型
        return restTemplate.postForObject(REST_URL_PREFIX+"/dept/add", dept, Boolean.class);
    }

    @RequestMapping("/register/consummer/dept/get/{deptNo}")
    public Dept get(@PathVariable("deptNo") Integer deptNo) {
        return restTemplate.getForObject(REST_URL_PREFIX+"/dept/get/"+deptNo, Dept.class);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/register/consummer/dept/get/all")
    public List<Dept> getAll() {
        return restTemplate.getForObject(REST_URL_PREFIX+"/dept/get/all", List.class);
    }

}