package org.etutoria.usersservice.Apis;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InboxController {

    @GetMapping("/inbox")
    public String inboxPage(
            @RequestParam(name = "token", required = true) String token,
            @RequestParam(name = "recipientId", required = true) String recipientId,
            Model model) {
        model.addAttribute("token", token);
        model.addAttribute("recipientId", recipientId);
        return "inbox";
    }
}