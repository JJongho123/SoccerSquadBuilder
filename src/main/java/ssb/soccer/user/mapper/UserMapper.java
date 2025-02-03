package ssb.soccer.user.mapper;

import ssb.soccer.user.model.LoginDto;
import ssb.soccer.user.model.User;

import java.util.List;

public interface UserMapper {
    List<User> findAllUsers();
    boolean createUser(User user);
    User findById(String id);

    User findByIdAndPassword(LoginDto loginDto);

}