package lf.springsecurityformlogin.controllers;

import lf.springsecurityformlogin.db.User;
import lf.springsecurityformlogin.db.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class MainController {

    private final UserRepo userRepo;

    public MainController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    @ResponseBody
    public String home(@ModelAttribute("user") User user) {
        return user.getUsername() + " " + user.getPassword();
    }

    @GetMapping("/home")
    public String homeq(RedirectAttributes rattrs) {
        rattrs.addFlashAttribute("user", userRepo.findByUsername("user"));
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        Model model) {
        String errorMsg = null;
        if (error != null) errorMsg = "Username or Password is incorrect";
        model.addAttribute("errorMsg", errorMsg);
        return "login";
    }

    @PostMapping("/login")
    public String loginPOST() {
        return "redirect:/login?error=true";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }
}
