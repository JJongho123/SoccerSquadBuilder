package ssb.soccer.user.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssb.soccer.com.encrypt.EncryptionService;
import ssb.soccer.redis.service.RedisService;
import ssb.soccer.user.model.LoginDto;
import ssb.soccer.user.model.User;
import ssb.soccer.user.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final EncryptionService encryptionService;

    public boolean login(LoginDto loginDto) {

        boolean result = false;
        String userId = loginDto.getUserId();
        String inputPwd = loginDto.getPasswd();

        User user = userService.findById(userId);
        if(user != null){
            result = encryptionService.verifyPassword(inputPwd, user.getPasswd(), user.getSalt());
        }
        return result;

    }

}
