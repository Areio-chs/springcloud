package com.service;

import com.bean.User;

import java.util.List;

public interface RegisterService {
    public void saveUser(User user);
    public List<User> getAll();
    public List<User> loginUser(User user);
    public boolean checkUser(String userName);
    public User login(String name , String word);
}
