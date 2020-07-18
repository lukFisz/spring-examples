package lf.springsecurityformlogin.controllers;

import lf.springsecurityformlogin.db.User;
import lf.springsecurityformlogin.db.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.security.PublicKey;
import java.util.List;

@Controller
public class MainController {

    private final String LOGIN_FAILURE_MESSAGE = "Username or/and password is incorrect";

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
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
    public String details(Authentication authentication, Model model, Principal principal) {
        model.addAttribute("auth", authentication);
        return "details";
    }


}
