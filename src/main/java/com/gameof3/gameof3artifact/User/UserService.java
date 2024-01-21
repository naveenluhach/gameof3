package com.gameof3.gameof3artifact.User;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
/**
 * Service class responsible for user-related operations.
 */
public class UserService {

    /**
     * Repository for accessing and manipulating User entities
     */
    private final UserRepository repository;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * Saves a new user with the specified status as online.
     *
     * @param user The user entity to be saved.
     */
    public void saveUser(User user) {
        User modifiedUser = user.updateStatus(user, Status.ONLINE);
        repository.save(modifiedUser);
    }

    /**
     * Disconnects a user by updating their status to offline.
     *
     * @param user The user entity to be disconnected.
     */
    public void disconnect(User user) {
        var storedUser = repository.findById(user.getNickName()).orElse(null);
        if (storedUser != null) {
            User modifiedStoredUser = storedUser.updateStatus(storedUser, Status.OFFLINE);
            repository.save(modifiedStoredUser);
        }
    }

    /**
     * Finds all connected users with the status set to online.
     *
     * @return List of connected User entities.
     */
    public List<User> findConnectedUsers() {
        return repository.findAllByStatus(Status.ONLINE);
    }
}
