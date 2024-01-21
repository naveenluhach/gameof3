package com.gameof3.gameof3artifact.ChatRoom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
/**
 * Represents a chat room entity in the application.
 */
public class ChatRoom {
    @Id
    /**
     * Unique identifier for the chat room
     */
    private String id;

    /**
     * Identifier for the chat associated with this room
     */
    private String chatId;

    /**
     * User ID of the sender in the chat room
     */
    private String senderId;

    /**
     * User ID of the recipient in the chat room
     */
    private String recipientId;
}
