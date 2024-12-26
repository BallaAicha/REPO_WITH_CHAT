package org.etutoria.usersservice.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatMessageService {
    private static final Logger logger = LoggerFactory.getLogger(ChatMessageService.class);
    private final ChatMessageRepository repository;
    private final SimpMessagingTemplate messagingTemplate;
    public ChatMessageService(ChatMessageRepository repository, SimpMessagingTemplate messagingTemplate) {
        this.repository = repository;
        this.messagingTemplate = messagingTemplate;
    }
    @Transactional
    public void sendMessage(String to, ChatMessage chatMessage, Jwt jwt) {
        String senderId = jwt.getSubject();
        String firstName = jwt.getClaimAsString("given_name");
        String lastName = jwt.getClaimAsString("family_name");
        chatMessage.setSenderId(senderId);
        chatMessage.setSenderName(firstName + " " + lastName);
        chatMessage.setRecipientId(to);
        chatMessage.setChatChannel(createAndOrGetChatChannel(senderId, to));
        chatMessage.setTstamp(generateTimeStamp());
        repository.save(chatMessage);
        messagingTemplate.convertAndSend("/topic/messages/" + chatMessage.getChatChannel(), chatMessage);
        messagingTemplate.convertAndSend("/topic/messages/" + senderId, chatMessage);
        messagingTemplate.convertAndSend("/topic/messages/" + to, chatMessage);
    }
    private String generateTimeStamp() {
        return String.valueOf(System.currentTimeMillis());
    }
    private String createAndOrGetChatChannel(String senderId, String to) {
        return senderId.compareTo(to) < 0 ? senderId + "-" + to : to + "-" + senderId;
    }


    @Transactional(readOnly = true)
    public List<ChatMessage> getMessagesByChannel(String chatChannel) {
        logger.info("Fetching messages for channel: {}", chatChannel);
        List<ChatMessage> messages = repository.findByChatChannel(chatChannel);
        logger.info("Found {} messages for channel: {}", messages.size(), chatChannel);
        return messages;
    }
}