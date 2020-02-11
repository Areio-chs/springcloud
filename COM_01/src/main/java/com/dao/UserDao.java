package com.dao;

import com.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDao {


    // 保存帐户信息
    @Insert("insert into user (username,password) values (#{username},#{password})")
    public void save(User user);


    @Select("select * from user where username = #{username}")
    public User findByUsername(String username);

}
