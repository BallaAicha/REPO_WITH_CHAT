package org.etutoria.usersservice.Apis;
import lombok.RequiredArgsConstructor;
import org.etutoria.usersservice.ChatMessage.ChatMessage;
import org.etutoria.usersservice.ChatMessage.ChatMessageService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Controller;
@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatMessageService chatMessageService;
    private final JwtDecoder jwtDecoder;

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to,
                            ChatMessage chatMessage,
                            @Header("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization header is missing or malformed.");
        }
        String token = authorizationHeader.substring(7);
        Jwt jwt = jwtDecoder.decode(token);
        chatMessageService.sendMessage(to, chatMessage, jwt);
    }






}
