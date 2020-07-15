package luk.fisz.springsecuritybasic.db;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private String role;
    private boolean active;

    public User(String username, String password, String role, boolean active){
        this.username = username;
        this.password = password;
        this.role = role;
        this.active = active;
    }
}
