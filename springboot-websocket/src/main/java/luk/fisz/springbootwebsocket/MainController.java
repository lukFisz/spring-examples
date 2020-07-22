package luk.fisz.springbootwebsocket;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final SimpMessagingTemplate template;

    public MainController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/chat/{roomID}")
    public void message(@DestinationVariable String roomID, Message message) {
        Message msg = new Message("Received message: " + message.getMsg());
        template.convertAndSend("/topic/chat/"+roomID, msg);
    }

}
