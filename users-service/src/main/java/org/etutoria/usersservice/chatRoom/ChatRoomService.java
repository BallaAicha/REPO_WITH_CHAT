package org.etutoria.usersservice.chatRoom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public Optional<String> getChatRoomId(
            String senderId,
            String receiverId,
            String listingId,
            boolean createNewRoomIfNotExist
    ) {
        return chatRoomRepository.findBySenderIdAndReceiverIdAndListingId(
                        senderId, receiverId, listingId
                ).map(ChatRoom::getId)
                .or(() -> {
                    if (createNewRoomIfNotExist) {
                        var chatId = createChat(senderId, receiverId, listingId);
                        return Optional.of(chatId);
                    }
                    return Optional.empty();
                });
    }

    private String createChat(String senderId, String receiverId, String listingId) {
        var chatId = String.format("%s_%s_%s", senderId, receiverId, listingId);
        ChatRoom senderRecipient = ChatRoom.builder()
                .chatId(chatId)
                .senderId(senderId)
                .receiverId(receiverId)
                .listingId(listingId)
                .build();

        ChatRoom recipientSender = ChatRoom.builder()
                .chatId(chatId)
                .senderId(receiverId)
                .receiverId(senderId)
                .listingId(listingId)
                .build();

        chatRoomRepository.save(senderRecipient);
        chatRoomRepository.save(recipientSender);
        return chatId;
    }
}