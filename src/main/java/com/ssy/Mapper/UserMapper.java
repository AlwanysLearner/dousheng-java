package com.ssy.Mapper;

import com.ssy.Entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from t_user")
    List<User> findAll();
    @Insert("insert into t_user(username,password) values (#{username},#{password})")
    Integer Create(User user);
    @Select("SELECT password FROM t_user where username=#{username}")
    String selectPasswordByUsername(String username);

    @Select("select count(*) from t_user where username=#{username}")
    int hasuser(String username);

    @Select("select * from t_user where username=#{username}")
    User selectByName(String username);
}
