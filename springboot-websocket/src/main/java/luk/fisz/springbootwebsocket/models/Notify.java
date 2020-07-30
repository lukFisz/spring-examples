package luk.fisz.springbootwebsocket.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notify {
    private String oldRoomId;
    private String newRoomId;
}
