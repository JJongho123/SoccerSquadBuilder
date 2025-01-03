package com.soccer.user.services;

import com.soccer.user.mappers.UserMapper;
import com.soccer.user.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    // 모든 사용자 조회
    public List<User> getAllUsers() {
        List<User> users = userMapper.findAllUsers();
        return users;
    }

    // id로 사용자 조회
    public User getUserById(int id) {
        return userMapper.findById(id);
    }
}