package ssb.soccer.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisTest {

    @Autowired
    private RedisTemplate<String ,String> redisTemplate;

    public void testRedisConnection() {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();

        // Redis에 값 저장
        valueOps.set("testKey", "testValue");

        // Redis에서 값 가져오기
        String value = valueOps.get("testKey");

        // 결과 출력
        if ("testValue".equals(value)) {
            System.out.println("Redis 연동 성공! 저장된 값: " + value);
        } else {
            System.out.println("Redis 연동 실패!");
        }
    }
}