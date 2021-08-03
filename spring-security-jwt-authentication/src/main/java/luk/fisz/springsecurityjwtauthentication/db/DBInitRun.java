package luk.fisz.springsecurityjwtauthentication.db;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DBInitRun implements CommandLineRunner {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public DBInitRun(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User("user", passwordEncoder.encode("user"), "USER", true);
        User admin = new User("admin", passwordEncoder.encode("admin"), "ADMIN", true);
        userRepo.save(user);
        userRepo.save(admin);
    }
}
