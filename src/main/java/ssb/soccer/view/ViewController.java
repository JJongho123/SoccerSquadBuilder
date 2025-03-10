package ssb.soccer.view;

import lombok.RequiredArgsConstructor;
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
    public ModelAndView showSoccerPage() {return new ModelAndView("forward:/html/soccer.html");}

    @GetMapping("/squadBuilder")
    public ModelAndView showsquadBuilderPage() {return new ModelAndView("forward:/html/squadBuilder.html");}

    @GetMapping("/register")
    public ModelAndView showRegisterPage() {
        return new ModelAndView("forward:/html/register.html");
    }

    @GetMapping("/team-manage")
    public ModelAndView showTeamPage() {
        return new ModelAndView("forward:/html/team-manage.html");
    }

}
