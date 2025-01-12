package ssb.soccer.user.service;

import ssb.soccer.user.mapper.UserMapper;
import ssb.soccer.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    // 모든 사용자 조회
    public List<User> getAllUsers() {
        return userMapper.findAllUsers();
    }

    // 사용자 저장
    public void createUser(User user) {
        userMapper.createUser(user);
    }

    // id로 사용자 조회
    public User getUserById(int id) {
        return userMapper.findById(id);
    }
}