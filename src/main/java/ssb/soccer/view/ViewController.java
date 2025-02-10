package ssb.soccer.view;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class ViewController {

    @GetMapping("/login")
    public ModelAndView showLoginPage() {
        return new ModelAndView("forward:/html/login.html");
    }

    @GetMapping("/soccer")
    public ModelAndView showSoccerPage() {
        return new ModelAndView("forward:/html/soccer.html");
    }

    @GetMapping("/register")
    public ModelAndView showRegisterPage() {
        return new ModelAndView("forward:/html/register.html");
    }

    @GetMapping("/error400")
    public ModelAndView show400ErrorPage() {
        return new ModelAndView("forward:/error/error400.html");
    }

    @GetMapping("/error404")
    public ModelAndView show404ErrorPage() {
        return new ModelAndView("forward:/error/error404.html");
    }

    @GetMapping("/error500")
    public ModelAndView show500ErrorPage() {
        return new ModelAndView("forward:/error/error500.html");
    }
}
