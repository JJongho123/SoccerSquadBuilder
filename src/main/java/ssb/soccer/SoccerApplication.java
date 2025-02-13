package ssb.soccer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("ssb.soccer.user.mapper")
public class SoccerApplication {


    public static void main(String[] args) {
        SpringApplication.run(SoccerApplication.class, args);
    }
}
