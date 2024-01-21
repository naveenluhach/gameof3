package com.gameof3.gameof3artifact.Game;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/**
 * Repository interface for accessing and manipulating Game entities in MongoDB.
 * The entity name in the database is "game"
 */
public interface GameRepository extends MongoRepository<Game, String> {

    /**
     * Finds a list of games by chatId using Spring Data JPA conventions.
     *
     * @param chatId The identifier of the chat.
     * @return List of Game entities associated with the specified chatId.
     */
    List<Game> findByChatId(String chatId);

}
