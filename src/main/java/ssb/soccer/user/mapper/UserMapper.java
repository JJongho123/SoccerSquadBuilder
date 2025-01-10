package ssb.soccer.user.mapper;

import ssb.soccer.user.model.User;

import java.util.List;

public interface UserMapper {
    List<User> findAllUsers();
    User findById(int id);
}