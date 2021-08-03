package luk.fisz.springsecurityjwtauthentication.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import luk.fisz.springsecurityjwtauthentication.db.User;
import luk.fisz.springsecurityjwtauthentication.db.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {

    private final PasswordEncoder passwordEncoder;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static
    class Message {
        String content;
    }

    @Data
    @NoArgsConstructor
    static class NewUserData {
        String username;
        String role;
    }

    private final UserRepo userRepo;

    public MainController(PasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.passwordEncoder = passwordEncoder;
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

    @PostMapping("/admin/add/user")
    public List<User> addUser(@RequestBody NewUserData newUserData) {
        if (userRepo.findByUsername(newUserData.getUsername()).isEmpty()) {
            User user = new User(
                    newUserData.getUsername(),
                    passwordEncoder.encode(newUserData.getUsername()),
                    newUserData.getRole(),
                    true);
            userRepo.save(user);
        } else {
            throw new RuntimeException("Username already exists.");
        }
        return userRepo.findAll();
    }


}
