package org.etutoria.usersservice.Config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class AuthorizationInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        // Extraire les en-têtes
        var headers = message.getHeaders();
        var simpHeaders = headers.get("nativeHeaders");

        if (simpHeaders instanceof Map) {
            Map<String, List<String>> nativeHeaders = (Map<String, List<String>>) simpHeaders;
            var authHeader = nativeHeaders.get("Authorization");

            if (authHeader != null && !authHeader.isEmpty()) {
                String token = authHeader.get(0);
                // Ici, tu peux valider ou stocker ce token pour un traitement ultérieur
                System.out.println("Authorization header: " + token);
            }
        }

        return message;
    }
}