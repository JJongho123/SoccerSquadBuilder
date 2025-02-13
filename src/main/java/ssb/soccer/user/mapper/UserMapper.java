package ssb.soccer.user.mapper;

import ssb.soccer.user.dto.LoginDto;
import ssb.soccer.user.model.User;

import java.util.List;

public interface UserMapper {
    List<User> findAllUsers();
    boolean createUser(User user);
    User findByIdAndPassword(LoginDto loginDto);

    User findById(String userId);

}