package org.etutoria.usersservice.Apis;
import lombok.RequiredArgsConstructor;
import org.etutoria.usersservice.ChatMessage.ChatMessage;
import org.etutoria.usersservice.ChatMessage.ChatMessageService;
import org.etutoria.usersservice.ChatMessage.ChatNotification;
import org.etutoria.usersservice.entities.Listing;
import org.etutoria.usersservice.listing.ListingService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatMessageService chatMessageService;
    private final ListingService listingService;

//    @MessageMapping("/chat")
//    public void processMessage(
//            @Payload ChatMessage chatMessage,
//            @AuthenticationPrincipal Jwt jwt
//    ) {
//        String senderId = jwt.getSubject();
//        if (!senderId.equals(chatMessage.getSenderId())) {
//            throw new RuntimeException("Sender ID does not match authenticated user");
//        }
//
//        Listing listing = listingService.getListing(chatMessage.getListingId(), jwt.getTokenValue());
//        chatMessage.setReceiverId(listing.getUserId());
//        chatMessage.setListing(listing); // Ajouter l'objet Listing
//
//        String firstName = jwt.getClaimAsString("given_name");
//        String lastName = jwt.getClaimAsString("family_name");
//        chatMessage.setFullName(firstName + " " + lastName);
//
//        ChatMessage save = chatMessageService.save(chatMessage, chatMessage.getListingId());
//        simpMessagingTemplate.convertAndSendToUser(
//                chatMessage.getReceiverId(),
//                "/queue/messages",
//                ChatNotification.builder()
//                        .id(save.getId())
//                        .senderId(chatMessage.getSenderId())
//                        .message(chatMessage.getMessage())
//                        .build()
//        );
//    }

    @PostMapping("/chat")
    public ResponseEntity<ChatMessage> sendMessage(
            @RequestBody ChatMessage chatMessage,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String senderId = jwt.getSubject();
        chatMessage.setSenderId(senderId);
        if (!senderId.equals(chatMessage.getSenderId())) {
            throw new RuntimeException("Sender ID does not match authenticated user");
        }
        Listing listing = listingService.getListing(chatMessage.getListingId(), jwt.getTokenValue());
        chatMessage.setReceiverId(listing.getUserId());
        chatMessage.setListing(listing);
        String firstName = jwt.getClaimAsString("given_name");
        String lastName = jwt.getClaimAsString("family_name");
        chatMessage.setSenderName(firstName + " " + lastName);
        ChatMessage save = chatMessageService.save(chatMessage, chatMessage.getListingId());
        simpMessagingTemplate.convertAndSendToUser(
                chatMessage.getReceiverId(),
                "/queue/messages",
                ChatNotification.builder()
                        .id(save.getId())
                        .senderId(chatMessage.getSenderId())
                        .message(chatMessage.getMessage())
                        .build()
        );
        return ResponseEntity.ok(save);
    }
    @GetMapping("/messages/{userId1}/{userId2}")
    public ResponseEntity<List<ChatMessage>> getMessagesBetweenUsers(
            @PathVariable("userId1") String userId1,
            @PathVariable("userId2") String userId2,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String authenticatedUserId = jwt.getSubject();
        if (!authenticatedUserId.equals(userId1) && !authenticatedUserId.equals(userId2)) {
            throw new RuntimeException("User not authorized to view these messages");
        }
        return ResponseEntity.ok(chatMessageService.getMessagesBetweenUsers(userId1, userId2));
    }
}