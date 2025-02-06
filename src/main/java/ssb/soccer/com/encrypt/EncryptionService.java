package ssb.soccer.com.encrypt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class EncryptionService {

    private final EncryptHandler handler;

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
