package luk.fisz.springsecurityjwtauthentication.controllers;

import luk.fisz.springsecurityjwtauthentication.db.User;
import luk.fisz.springsecurityjwtauthentication.db.UserRepo;
import luk.fisz.springsecurityjwtauthentication.security.jwt.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

import static luk.fisz.springsecurityjwtauthentication.security.jwt.JwtProperties.HEADER_NAME;

@RestController
@RequestMapping("/api")
public class MainController {

    private final UserRepo userRepo;

    public MainController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }

    @GetMapping("/users")
    public List<User> users() {
        return userRepo.findAll();
    }

    @PostMapping("/user/add")
    public List<User> userAdd(@RequestBody User user) {
        userRepo.save(user);
        return userRepo.findAll();
    }
}
