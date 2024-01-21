package com.gameof3.gameof3artifact.User;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "user")
/**
 * Represents a user entity in the application.
 */
public class User {
    /**
     * Unique identifier for the user, using the nickname.
     */
    @Id
    private String nickName;

    /**
     * Full name of the user.
     */
    private String fullName;

    /**
     * Status of the user (e.g., "ONLINE", "OFFLINE").
     */
    private Status status;
}