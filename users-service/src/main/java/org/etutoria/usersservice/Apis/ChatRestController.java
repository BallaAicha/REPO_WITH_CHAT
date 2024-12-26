package org.etutoria.usersservice.Apis;

import lombok.RequiredArgsConstructor;
import org.etutoria.usersservice.ChatMessage.ChatMessage;
import org.etutoria.usersservice.ChatMessage.ChatMessageService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatRestController {
    private final ChatMessageService chatMessageService;
    private final JwtDecoder jwtDecoder;

    @GetMapping("/api/chat/{channel}")
    public List<ChatMessage> getMessagesByChannel(@PathVariable String channel,
                                                  @RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization header is missing or malformed.");
        }

        String token = authorizationHeader.substring(7);
        Jwt jwt = jwtDecoder.decode(token);
        String userId = jwt.getSubject();

        if (!channel.contains(userId)) {
            throw new IllegalArgumentException("User is not authorized to view messages in this channel.");
        }

        return chatMessageService.getMessagesByChannel(channel);
    }
}