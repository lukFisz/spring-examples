package luk.fisz.springbootwebsocket.db.repos;

import luk.fisz.springbootwebsocket.db.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

}
