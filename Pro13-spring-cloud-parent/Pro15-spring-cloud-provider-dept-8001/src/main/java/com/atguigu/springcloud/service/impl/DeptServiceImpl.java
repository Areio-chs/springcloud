package com.atguigu.springcloud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.cloud.entities.Dept;
import com.atguigu.springcloud.mapper.DeptMapper;
import com.atguigu.springcloud.service.DeptService;
//页面不变
@Service
public class DeptServiceImpl implements DeptService {
	
	@Autowired
	private DeptMapper deptMapper;

	@Override
	public Dept findById(Integer deptNo) {
		return deptMapper.findById(deptNo);
	}

	@Override
	public List<Dept> findAll() {
		return deptMapper.findAll();
	}

	@Override
	public boolean addDept(Dept dept) {
		return deptMapper.addDept(dept);
	}

}
