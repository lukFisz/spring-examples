package luk.fisz.springbootwebsocket;

import luk.fisz.springbootwebsocket.models.ChatRoom;
import luk.fisz.springbootwebsocket.models.Message;
import luk.fisz.springbootwebsocket.models.Notify;
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

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class MainController {

    public static final String SESSION_USERNAME = "__username";
    public static final String ERROR_USERNAME_TAKEN = "Choose different username.";
    private final SimpMessagingTemplate template;
    private final List<String> users = new ArrayList<>();
    private final List<ChatRoom> chatRooms = new ArrayList<>();
    private final Map<String, List<String>> participants = new HashMap<>();


    private boolean b = true;



    public MainController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/room/{roomID}")
    public void message(@DestinationVariable String roomID,
                        @Payload Message message,
                        SimpMessageHeaderAccessor messageHeaderAccessor) {

        if (messageHeaderAccessor.getSessionAttributes() == null) return;

        Object username = messageHeaderAccessor.getSessionAttributes().get(SESSION_USERNAME);
        message.setSender(username.toString());
        template.convertAndSend("/topic/room/" + roomID, message);
    }

    @MessageMapping("/notify")
    public void notify(@Payload Notify notify,
                       SimpMessageHeaderAccessor messageHeaderAccessor) {

        if (messageHeaderAccessor.getSessionAttributes() == null) return;

        Object username = messageHeaderAccessor.getSessionAttributes().get(SESSION_USERNAME);
        if (notify.getOldRoomId() != null) {
            participants.get(notify.getOldRoomId()).remove(username.toString());
            template.convertAndSend("/topic/room/" + notify.getOldRoomId(), new Message("Has left the chat", username.toString()));
        }
        participants.get(notify.getNewRoomId()).add(username.toString());
        template.convertAndSend("/topic/room/" + notify.getNewRoomId(), new Message("Has join the chat", username.toString()));
    }

    @GetMapping("/")
    public String chat(HttpSession session,
                       Model model) {

        if (b){
            for (int i = 0; i < 4; i++) {
                String uuid = generateRandomUUID();
                chatRooms.add(new ChatRoom(uuid, "name" + i));
                participants.put(uuid, new ArrayList<>());
            }
            b = false;
        }

        if (session.getAttribute(SESSION_USERNAME) == null) return "redirect:/login";

        model.addAttribute("username", session.getAttribute(SESSION_USERNAME));

        return "chat";
    }

    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false) String error,
                        Model model,
                        HttpSession session) {

        if (session.getAttribute(SESSION_USERNAME) != null) return "redirect:/";
        if (error != null) model.addAttribute("error", ERROR_USERNAME_TAKEN);

        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam(name = "username") String username,
                        HttpSession session) {

        if (session.getAttribute(SESSION_USERNAME) != null) return "redirect:/";
        if (users.contains(username)) return "redirect:/login?error";

        users.add(username);
        session.setAttribute(SESSION_USERNAME, username);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        Object username = session.getAttribute(SESSION_USERNAME);

        users.remove(username.toString());
        session.removeAttribute(SESSION_USERNAME);

        return "redirect:/login";
    }

    @PostMapping("/fetch/chatrooms")
    @ResponseBody
    public List<ChatRoom> fetchChatRooms() {
        return chatRooms;
    }

    /*TODO: new chat room*/
    @GetMapping("/create/chatroom")
    public String createChatRoom() {
        String uuid = generateRandomUUID();
        chatRooms.add(new ChatRoom(generateRandomUUID(), "test_name"));
        participants.put(uuid, new ArrayList<>());
        return "redirect:/";
    }

    @PostMapping("/fetch/participants")
    @ResponseBody
    public List<String> fetchParticipants(@RequestParam(name = "roomID") String roomID) {
        return participants.getOrDefault(roomID, new ArrayList<>());
    }

    private String generateRandomUUID() {
        return UUID.randomUUID().toString();
    }
}
