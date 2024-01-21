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

    public String getNickName() {
        return nickName;
    }

    private void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFullName() {
        return fullName;
    }

    private void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Status getStatus() {
        return status;
    }

    private void setStatus(Status status) {
        this.status = status;
    }

    public User updateStatus(User user, Status status){
        user.setStatus(status);
        return user;
    }
}