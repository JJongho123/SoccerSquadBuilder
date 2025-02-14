package ssb.soccer.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssb.soccer.com.encrypt.EncryptionService;
import ssb.soccer.com.exception.CustomApiException;
import ssb.soccer.com.exception.ExceptionEnum;
import ssb.soccer.passwdPolicy.service.PasswdVaildationService;
import ssb.soccer.user.mapper.UserMapper;
import ssb.soccer.user.dto.LoginDto;
import ssb.soccer.user.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    private final PasswdVaildationService passwdVaildationService;

    private final EncryptionService encryptionService;

    // 모든 사용자 조회
    public List<User> getAllUsers() {
        return userMapper.findAllUsers();
    }

    // 사용자 저장
    public void createUser(User user) {
        if (!passwdVaildationService.isPolicySatisfaction(user.getPasswd())) {
            throw new CustomApiException(ExceptionEnum.PASSWD_POLICY_ERROR);
        }

        Map<String, String> map = encryptionService.generateHashPassWordAndSalt(user.getPasswd());
        user.setSalt(map.get("salt"));
        user.setPasswd(map.get("password"));

        boolean isCreated = userMapper.createUser(user);
        if (!isCreated) {
            throw new CustomApiException(ExceptionEnum.USER_CREATION_FAILED);
        }
    }

    public boolean findByIdAndPassword(LoginDto loginDto) {
        return userMapper.findByIdAndPassword(loginDto) != null;
    }

    public User findById(String userId) {
        return userMapper.findById(userId);
    }
}