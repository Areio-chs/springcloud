package com.service.impl;

import com.bean.User;
import com.bean.UserExample;
import com.bean.UserExample.Criteria;
import com.dao.UserMapper;
import com.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    UserMapper userMapper;
    //保存所有用户
    public void saveUser(User user){
        userMapper.insertSelective(user);
    }
    //查询所有用户
    public List<User> getAll(){
        return userMapper.selectByExample(null);
    }
    //验证登录用户,根据用户名查询其密码，判断是否与提交的一致，逆向工程一般都是一整行都输出的
    public List<User> loginUser(User user){
        UserExample example=new UserExample();
        Criteria criteria = example.createCriteria();
        //等价于where username=
        criteria.andUsernameEqualTo(user.getUsername());
        //查出来与登录的用户名的那一行记录 然后去controller判断其密码是否与数据库中一样
        List<User> users = userMapper.selectByExample(example);
        return users;

    }

    /**
     * 检验用户名是否可用
     *
     * @param userName
     * @return  true：代表当前姓名可用   fasle：不可用
     */
    public boolean checkUser(String userName) {
        // TODO Auto-generated method stub
        UserExample example = new UserExample();
        Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(userName);
        long count = userMapper.countByExample(example);
        return count == 0;//count数据库中等于输入的名字的条数
    }

    public User login(String name , String word){
        UserExample example = new UserExample();
        Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(name);
        criteria.andPasswordEqualTo(word);
        User user = (User) userMapper.selectByExample(example);
        return user;
    }
}