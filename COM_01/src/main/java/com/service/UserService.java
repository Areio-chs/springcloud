package com.service;


import com.domain.User;

public interface UserService {


    // 保存帐户信息
    public boolean regist(User user);

    public User login(String username);

}
