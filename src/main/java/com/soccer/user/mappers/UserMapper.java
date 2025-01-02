package com.soccer.user.mappers;

import com.soccer.user.models.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findAllUsers();
    User findById(int id);
}