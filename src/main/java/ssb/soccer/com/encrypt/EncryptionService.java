package ssb.soccer.com.encrypt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class EncryptionService {

    private final EncryptHandler handler;

    /**
     *  사용자의 비밀번호를 암호화하여 해시된 비밀번호와 솔트를 생성하는 함수.
     *
     *  @param password 패스워드
     *  return 해시된 비밀번호와 솔트가 포함된 HashMap
     *
     * */
    public HashMap<String, String> generateHashPassWordAndSalt(String password) {

        HashMap<String, String> result = new HashMap<>();

        String salt = handler.generateSalt();
        String hashPassword = handler.hashPassword(password, salt);

        result.put("password", hashPassword);
        result.put("salt", salt);

        return result;
    }

    public boolean verifyPassword(String inputPwd, String storeHashPwd, String salt){
        return handler.verifyPassword(inputPwd, storeHashPwd, salt);
    }
}
