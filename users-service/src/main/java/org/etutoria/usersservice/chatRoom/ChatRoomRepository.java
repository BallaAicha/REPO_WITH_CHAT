package org.etutoria.usersservice.chatRoom;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    Optional<ChatRoom> findBySenderIdAndReceiverIdAndListingId(String senderId, String receiverId, String listingId);
}
