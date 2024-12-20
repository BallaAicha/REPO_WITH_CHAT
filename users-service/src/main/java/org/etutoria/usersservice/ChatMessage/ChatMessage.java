package org.etutoria.usersservice.ChatMessage;
import lombok.*;
import org.etutoria.usersservice.entities.Listing;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
@Builder
public class ChatMessage {
    @Id
    private String id;
    private String chatId;
    private String senderId;
    private String receiverId;
    private String message;
    private Date timestamp;
    private String SenderName;
    private String listingId;
    private Listing listing;
}