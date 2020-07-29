package luk.fisz.springsecurityjwtauthentication.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import luk.fisz.springsecurityjwtauthentication.db.User;
import luk.fisz.springsecurityjwtauthentication.db.UserRepo;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Message{
        String content;
    }

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

    @GetMapping("/admin")
    public Message admin() {
        return new Message("Only admin can see this!");
    }

    @PostMapping("/user/add")
    public List<User> userAdd(@RequestBody User user) {
        userRepo.save(user);
        return userRepo.findAll();
    }
}