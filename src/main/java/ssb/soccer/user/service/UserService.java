package ssb.soccer.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssb.soccer.user.auth.PasswdVaildationService;
import ssb.soccer.user.mapper.UserMapper;
import ssb.soccer.user.model.LoginDto;
import ssb.soccer.user.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    private final PasswdVaildationService passwdVaildationService;

    // 모든 사용자 조회
    public List<User> getAllUsers() {
        return userMapper.findAllUsers();
    }

    // 사용자 저장
    public boolean createUser(User user) {

        boolean result = passwdVaildationService.isPolicySatisfaction(user.getPasswd());
        if (result)
            result = userMapper.createUser(user);
        else
            throw new IllegalArgumentException("비밀번호가 정책을 만족하지 않습니다.");

        return result;
    }

    public boolean findByIdAndPassword(LoginDto loginDto) {
        return userMapper.findByIdAndPassword(loginDto) != null;
    }
}