package com.soccer.user.mappers;

import com.soccer.user.models.User;

import java.util.List;

public interface UserMapper {
    List<User> findAllUsers();
    User findById(int id);
}