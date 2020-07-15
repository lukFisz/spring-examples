package luk.fisz.springsecuritybasic.db;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DBInitRun implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    public DBInitRun(PasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    @Override
    public void run(String... args) throws Exception {

        User user = new User("user", passwordEncoder.encode("user123"), "USER", true);
        User admin = new User("admin", passwordEncoder.encode("admin123"), "ADMIN", true);

        userRepo.save(user);
        userRepo.save(admin);
    }

}
