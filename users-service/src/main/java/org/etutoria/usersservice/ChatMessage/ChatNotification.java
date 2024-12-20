package org.etutoria.usersservice.ChatMessage;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
@Builder
public class ChatNotification {
    private String id;
    private String senderId;
    private String receiverId;
    private String message;

}
