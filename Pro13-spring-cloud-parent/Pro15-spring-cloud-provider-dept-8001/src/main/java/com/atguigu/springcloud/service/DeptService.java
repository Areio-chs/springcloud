package com.atguigu.springcloud.service;

import java.util.List;

import com.atguigu.cloud.entities.Dept;
//页面不变
public interface DeptService {
	
	Dept findById(Integer deptNo);

	List<Dept> findAll();

	boolean addDept(Dept dept);

}
