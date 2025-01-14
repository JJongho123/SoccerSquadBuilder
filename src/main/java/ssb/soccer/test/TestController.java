package ssb.soccer.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/test-error")
    public String testError() {
        // 의도적으로 예외를 발생시킴
        throw new RuntimeException("테스트용 예외 발생!");
    }
}
