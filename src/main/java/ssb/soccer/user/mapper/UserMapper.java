package ssb.soccer.user.mapper;

import ssb.soccer.user.dto.UserWithTeamDTO;
import ssb.soccer.user.model.User;

import java.util.List;

public interface UserMapper {

    List<User> findAllUsers();
    boolean createUser(User user);
    User findByUserId(String userId);
    UserWithTeamDTO findUserWithTeam(String userId);
    List<User> findUserList(int teamId);
    User findById(int id);

    void updateUser(User user);
}