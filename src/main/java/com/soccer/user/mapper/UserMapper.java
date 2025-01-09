package com.soccer.user.mapper;

import com.soccer.user.model.User;

import java.util.List;

public interface UserMapper {
    List<User> findAllUsers();
    User findById(int id);
}