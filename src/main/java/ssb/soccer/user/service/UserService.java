package ssb.soccer.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssb.soccer.com.encrypt.EncryptionService;
import ssb.soccer.com.exception.CustomApiException;
import ssb.soccer.com.exception.ExceptionEnum;
import ssb.soccer.passwdPolicy.service.PasswdVaildationService;
import ssb.soccer.user.mapper.UserMapper;
import ssb.soccer.user.model.LoginDto;
import ssb.soccer.user.model.User;

import java.util.HashMap;
import java.util.List;

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
    public boolean createUser(User user) {

        boolean result = passwdVaildationService.isPolicySatisfaction(user.getPasswd());

        if (result){

            HashMap<String, String > map = encryptionService.generateHashPassWordAndSalt(user.getPasswd());
            user.setSalt(map.get("salt"));
            user.setPasswd(map.get("password"));
            result = userMapper.createUser(user);

        }
        else
            throw new CustomApiException(ExceptionEnum.PASSWD_POLICY_ERROR);

        return result;
    }

    public boolean findByIdAndPassword(LoginDto loginDto) {
        return userMapper.findByIdAndPassword(loginDto) != null;
    }

    public User findById(String userId) {
        return userMapper.findById(userId);
    }
}