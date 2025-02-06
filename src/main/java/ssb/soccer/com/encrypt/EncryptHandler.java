package ssb.soccer.com.encrypt;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Component
public class EncryptHandler {

    private final String SHA_256 = "SHA-256";

    // 솔트 생성
    public String generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[20];
        sr.nextBytes(salt);

        StringBuilder sb = new StringBuilder();
        for (byte b : salt) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // 비밀번호 + 솔트 해싱
    public String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(SHA_256);
            md.update((password + salt).getBytes());
            byte[] hashedBytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // 비밀번호 유효성 검사
    public boolean verifyPassword(String inputPwd, String storeHashPwd, String salt) {
        String hashedInput = hashPassword(inputPwd, salt);
        return hashedInput.equals(storeHashPwd);
    }

}
