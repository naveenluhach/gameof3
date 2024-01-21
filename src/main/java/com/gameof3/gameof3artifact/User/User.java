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

    /**
     * Getter for 'nickName'.
     * @return The nickname of the user.
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * Setter for 'nickName'.
     * @param nickName The nickname to set for the user.
     */
    private void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * Getter for 'fullName'.
     * @return The full name of the user.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Setter for 'fullName'.
     * @param fullName The full name to set for the user.
     */
    private void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Getter for 'status'.
     * @return The status of the user.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Setter for 'status'.
     * @param status The status to set for the user.
     */
    private void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Static method to update the status of an existing User instance.
     * @param user The User instance to update.
     * @param status The new status to set for the user.
     * @return The updated User instance.
     */
    public static User updateStatus(User user, Status status){
        // Update the status using the setter
        user.setStatus(status);

        // Return the updated User instance
        return user;
    }
}