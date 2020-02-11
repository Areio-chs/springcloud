package com.lrm.service;

import com.lrm.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by limi on 2017/10/16.
 */
public interface TypeService {

    Type saveType(Type type);//增加分类

    Type getType(Long id);//查询分类

    Type getTypeByName(String name);

    Page<Type> listType(Pageable pageable);//分页查询

    List<Type> listType();

    List<Type> listTypeTop(Integer size);

    Type updateType(Long id,Type type);//修改分类，通过id查询到这个分类再修改

    void deleteType(Long id);
}
