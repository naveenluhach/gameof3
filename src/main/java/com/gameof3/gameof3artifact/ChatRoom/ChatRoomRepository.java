package com.gameof3.gameof3artifact.ChatRoom;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

/**
 * Repository interface for accessing and manipulating ChatRoom entities in MongoDB.
 * The entity name in the database is "chat_room"
 */
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {

    /**
     * Finds a chat room by senderId and recipientId using Spring Data JPA conventions.
     *
     * @param senderId    User ID of the sender.
     * @param recipientId User ID of the recipient.
     * @return Optional containing the ChatRoom entity if found.
     */
    Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String recipientId);

    /**
     * Custom query to find a chat room by senderId and recipientId.
     *
     * @param senderId    User ID of the sender.
     * @param recipientId User ID of the recipient.
     * @return The ChatRoom entity if found.
     */
    @Query("{$and :[{senderId: ?0},{recipientId: ?1}]}")
    ChatRoom findBySenderIdAndRecipientIdcustom(String senderId, String recipientId);
}
