package org.etutoria.usersservice.ChatMessage;

import lombok.RequiredArgsConstructor;
import org.etutoria.usersservice.chatRoom.ChatRoomService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage, String listingId) {
        var chatId = chatRoomService.getChatRoomId(
                chatMessage.getSenderId(),
                chatMessage.getReceiverId(),
                listingId,
                true
        ).orElseThrow();
        chatMessage.setChatId(chatId);
        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> getChatMessages(String senderId, String receiverId) {
        var chatId = chatRoomService.getChatRoomId(senderId, receiverId, null, false);
        return chatId.map(chatMessageRepository::findByChatId)
                .orElse(new ArrayList<>());
    }



    public List<ChatMessage> getMessagesBetweenUsers(String userId1, String userId2) {
        return chatMessageRepository.findAllBySenderIdOrReceiverId(userId1, userId2);
    }


}