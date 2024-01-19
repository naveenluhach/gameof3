package com.gameof3.gameof3artifact.ChatRoom;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String recipientId);

    @Query("{$and :[{senderId: ?0},{recipientId: ?1}]}")
    ChatRoom findBySenderIdAndRecipientIdcustom(String senderId, String recipientId);
}
