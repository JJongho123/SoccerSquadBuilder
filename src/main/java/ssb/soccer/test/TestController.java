package ssb.soccer.test;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import ssb.soccer.com.exception.ApiException;
import ssb.soccer.com.exception.ExceptionEnum;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/error")
public class TestController {

    @GetMapping("/400")
    public String testError400(){
        throw new IllegalArgumentException(ExceptionEnum.RUNTIME_EXCEPTION.getMessage());
    }

    /**
     *    Spring Security 관련 예외 주석
     * */
//    @GetMapping("/401")
//    public String testError401(){
//        throw new AuthenticationException(ExceptionEnum.AUTHENTICATION_EXCEPTION.getMessage());
//    }

//    @GetMapping("/403")
//    public String testError403(){
//        throw new AccessDeniedException(ExceptionEnum.SECURITY.getMessage());
//    }

    @GetMapping("/404")
    public String testError404(){
        throw new NoSuchElementException(ExceptionEnum.NO_RESOURCE_FOUND_EXCEPTION.getMessage());
    }

    @GetMapping("/500")
    public String testError500(){
        throw new NullPointerException(ExceptionEnum.INTERNAL_SERVER_ERROR.getMessage());
    }

    @RequestMapping("/custom")
    public void testErrorCustom(HttpServletRequest request) {
        throw new ApiException(ExceptionEnum.SECURITY);
    }
}
