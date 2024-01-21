package com.gameof3.gameof3artifact.chat;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Repository interface for accessing and manipulating PlayerMessage entities in MongoDB.
 * Note: The entity name in the database is "player_message"
 */
public interface ChatMessageRepository extends MongoRepository<PlayerMessage, String> {

    /**
     * Finds all player messages by chatId.
     *
     * @param chatId The identifier of the chat.
     * @return List of PlayerMessage entities associated with the specified chatId.
     */
    List<PlayerMessage> findByChatId(String chatId);
}
