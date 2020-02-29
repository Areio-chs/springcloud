package com.atguigu.springcloud.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.atguigu.cloud.entities.Dept;
//页面不改
@Mapper // 作用类似于@Service、@Controller注解。另一个方案是在主启动类上使用@MapperScan
public interface DeptMapper {
	
	Dept findById(Integer deptNo);
	
	List<Dept> findAll();
	
	boolean addDept(Dept dept);

}
