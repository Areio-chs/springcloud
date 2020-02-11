package com.service.impl;

import com.dao.UserDao;
import com.domain.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public boolean regist(User user) {
        System.out.println("业务层：保存帐户...");
        User u = userDao.findByUsername(user.getUsername());
        //判断u是否为null
        if(u != null){
            //用户名存在，注册失败
            return false;
        }
        userDao.save(user);
        return true;
    }

    @Override
    public User login(String username) {
        System.out.println("业务层：登录帐户...");
        return userDao.findByUsername(username);
    }

}
