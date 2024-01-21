package com.gameof3.gameof3artifact.User;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Repository interface for accessing and manipulating User entities in MongoDB.
 * The entity name in the database is "user"
 */
public interface UserRepository  extends MongoRepository<User, String> {

    /**
     * Finds all users with a specific status using Spring Data JPA conventions.
     *
     * @param status The status to filter users.
     * @return List of User entities with the specified status.
     */
    List<User> findAllByStatus(Status status);
}
