package luk.fisz.springbootwebsocket.db.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    @ManyToMany
//    TODO: need to finish many to many relation
    private Set<ChatRoom> chatRooms = new HashSet<>();

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.active = true;
    }
}
