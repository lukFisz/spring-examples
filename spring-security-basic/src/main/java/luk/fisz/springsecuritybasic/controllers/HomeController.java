package luk.fisz.springsecuritybasic.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping(value = "/user", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String user() {
        return "user";
    }

    @GetMapping(value = "/admin", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String admin() {
        return "admin";
    }

    @GetMapping("/details")
    public String details(Principal principal, Model model) {
        model.addAttribute("principal", principal);
        return "details";
    }
}
