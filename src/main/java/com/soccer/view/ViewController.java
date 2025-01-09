package com.soccer.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController {
    @GetMapping("/login")
    public ModelAndView showLoginPage() {
        return new ModelAndView("forward:/login.html");
    }

    @GetMapping("/soccer")
    public ModelAndView showIndexPage() {
        return new ModelAndView("forward:/soccer.html");
    }
}
