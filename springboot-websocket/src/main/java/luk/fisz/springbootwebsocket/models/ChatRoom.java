package luk.fisz.springbootwebsocket.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRoom {
    private String id;
    private String name;
}