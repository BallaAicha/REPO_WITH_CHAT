package org.etutoria.usersservice.ChatMessage;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String > {
    List<ChatMessage> findByChatId(String chatId);
    List<ChatMessage> findBySenderIdAndReceiverId(String senderId, String receiverId);
    List<ChatMessage> findAllBySenderIdOrReceiverId(String userId1, String userId2);
}
