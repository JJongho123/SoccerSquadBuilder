package ssb.soccer.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RedisTestRunner implements CommandLineRunner {

    private final RedisTest redisTestService;

    public RedisTestRunner(RedisTest redisTestService) {
        this.redisTestService = redisTestService;
    }

    @Override
    public void run(String... args) {
        redisTestService.testRedisConnection();
    }
}