package com.soccer.user.mapper;

import com.soccer.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findAllUsers();
    User findById(int id);
}
