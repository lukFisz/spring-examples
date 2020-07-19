package lf.springsecurityformlogin.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    private final String LOGIN_FAILURE_MESSAGE = "Username or/and password is incorrect";

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login(Authentication auth) {
        if (auth == null)
            return "login";
        else
            return "redirect:/";
    }

    @PostMapping("/login")
    public String loginError(Model model) {
        model.addAttribute("errorMsg", LOGIN_FAILURE_MESSAGE);
        return "login";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/details")
    public String details(Authentication authentication, Model model) {
        model.addAttribute("auth", authentication);
        return "details";
    }

}
