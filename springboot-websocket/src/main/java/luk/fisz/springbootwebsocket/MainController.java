package luk.fisz.springbootwebsocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.Principal;
import java.util.*;

@Controller
public class MainController {

    public static final String SESSION_USERNAME = "__username";
    public static final String ERROR_USERNAME_TAKEN = "Choose different username.";
    private final SimpMessagingTemplate template;
    private final List<String> users = new ArrayList<>();
    private final List<ChatRoom> chatRooms = new ArrayList<>();


    public MainController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Message {
        private String content;
        private String sender;
    }

    @Data
    @AllArgsConstructor
    static class ChatRoom {
        private String id;
        private String name;
    }


    @MessageMapping("/room/{roomID}")
    public void message(@DestinationVariable String roomID,
                        @Payload Message message,
                        SimpMessageHeaderAccessor messageHeaderAccessor) {

        if (messageHeaderAccessor.getSessionAttributes() == null)
            return;

        Object username = messageHeaderAccessor.getSessionAttributes().get(SESSION_USERNAME);
        message.setSender(username.toString());
        template.convertAndSend("/topic/room/" + roomID, message);
    }

    @GetMapping("/")
    public String chat(HttpSession session) {

        session.setAttribute(SESSION_USERNAME, "user");
        for (int i = 0; i < 4; i++) {
            UUID uuid = UUID.randomUUID();
            chatRooms.add(new ChatRoom(uuid.toString(), "name" + i));
        }

        if (session.getAttribute(SESSION_USERNAME) == null) return "redirect:/login";
        return "chat";
    }

    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false) String error,
                        Model model,
                        HttpSession session) {
        if (session.getAttribute(SESSION_USERNAME) == null) return "redirect:/";
        if (error != null) model.addAttribute("error", ERROR_USERNAME_TAKEN);
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam(name = "username") String username,
                        HttpSession session) {
        if (session.getAttribute(SESSION_USERNAME) != null) return "redirect:/";
        if (users.contains(username)) {
            return "redirect:/login?error";
        }
        users.add(username);
        return "redirect:/";
    }

    @PostMapping("/fetch/chatrooms")
    @ResponseBody
    public List<ChatRoom> fetchChatRooms() {
        return chatRooms;
    }

    @GetMapping("/create/chatroom")
    public String createChatRoom() {
        UUID uuid = UUID.randomUUID();
        chatRooms.add(new ChatRoom(uuid.toString(), "test_name"));
        return "redirect:/";
    }
}
