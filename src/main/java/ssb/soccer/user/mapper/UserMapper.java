package ssb.soccer.user.mapper;

import ssb.soccer.user.model.LoginDto;
import ssb.soccer.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserMapper {
    List<User> findAllUsers();
    boolean createUser(User user);
    Optional<User> findById(String id);

    User findByIdAndPassword(LoginDto loginDto);

}