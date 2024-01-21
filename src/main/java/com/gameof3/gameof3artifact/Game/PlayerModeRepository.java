package com.gameof3.gameof3artifact.Game;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface for accessing and manipulating PlayerMode entities in MongoDB.
 * The entity name in the database is "player_mode"
 */
public interface PlayerModeRepository extends MongoRepository<PlayerMode, String> {

    /**
     * Finds a player mode by user using Spring Data JPA conventions.
     *
     * @param user The user for whom to retrieve the player mode.
     * @return The PlayerMode entity representing the player mode for the specified user.
     */
    PlayerMode findByPlayer(String user);
}
