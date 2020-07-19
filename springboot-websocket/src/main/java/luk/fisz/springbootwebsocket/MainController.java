package luk.fisz.springbootwebsocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final SimpMessagingTemplate template;

    public MainController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public Message message(Message message) throws InterruptedException {
        Thread.sleep(1000L);
        return new Message("Received message: " + message.getMsg());
    }

    @GetMapping("/")
    public String home() {
        return "index.html";
    }

    @Scheduled(fixedRate = 2000)
    public void fireGreeting() {
        this.template.convertAndSend("/topic/chat", new Message("Hello"));
    }

}
