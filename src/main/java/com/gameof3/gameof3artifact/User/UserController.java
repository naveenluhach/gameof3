package com.gameof3.gameof3artifact.User;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
/**
 * Controller class for managing user-related operations.
 */
public class UserController {

    private final UserService userService;

    /**
     * Handles the WebSocket message for adding a user.
     * Saves the user using the UserService and broadcasts the user to the public channel.
     * @param user The user to be added
     * @return The added user
     */
    @MessageMapping("/user.addUser")
    @SendTo("/user/public")
    public User addUser(
            @Payload User user
    ) {
        userService.saveUser(user);
        return user;
    }

    /**
     * Handles the WebSocket message for disconnecting a user.
     * Disconnects the user using the UserService and broadcasts the user to the public channel.
     * @param user The user to be disconnected
     * @return The disconnected user
     */
    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/public")
    public User disconnectUser(
            @Payload User user
    ) {
        userService.disconnect(user);
        return user;
    }

    /**
     * Retrieves a list of connected users.
     * Calls the findConnectedUsers method of the UserService and returns the list.
     * @return ResponseEntity containing a list of connected users
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> findConnectedUsers() {
        return ResponseEntity.ok(userService.findConnectedUsers());
    }
}
