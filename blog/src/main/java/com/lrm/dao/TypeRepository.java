package com.lrm.dao;

import com.lrm.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by limi on 2017/10/16.
 */
public interface TypeRepository extends JpaRepository<Type,Long> {
//继承了...里面有很多方法
    Type findByName(String name);

    //List<Type>  findTop6ByBlogs 不会根据这六条大小排序

    @Query("select t from Type t")//自定义查询  第二个t是别名
    List<Type> findTop(Pageable pageable);//按分页的方式去查询，分页对象里面自动有排序且设置显示六条
}
