package luk.fisz.springbootwebsocket.db.repos;

import luk.fisz.springbootwebsocket.db.models.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepo extends JpaRepository<ChatRoom, Long> {
}
