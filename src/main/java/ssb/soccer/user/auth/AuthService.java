package ssb.soccer.user.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssb.soccer.redis.service.RedisService;
import ssb.soccer.user.model.LoginDto;
import ssb.soccer.user.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final RedisService redisService;

    public Boolean login(LoginDto loginDto) {
        Boolean result = false;
        String userId = loginDto.getUserId();
        if(redisService.exists(userId)){
            return true;
        }
        else1{
            userService.findByIdAndPassword(loginDto)
            return true;
        }
        return result;

    }
}
