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

    /**
     * Getter for 'id'.
     * @return The unique identifier of the chat room.
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for 'id'.
     * @param id The unique identifier to set for the chat room.
     */
    private void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for 'chatId'.
     * @return The identifier of the chat associated with this room.
     */
    public String getChatId() {
        return chatId;
    }

    /**
     * Setter for 'chatId'.
     * @param chatId The identifier to set for the associated chat.
     */
    private void setChatId(String chatId) {
        this.chatId = chatId;
    }

    /**
     * Getter for 'senderId'.
     * @return The identifier of the sender in the chat.
     */
    public String getSenderId() {
        return senderId;
    }

    /**
     * Setter for 'senderId'.
     * @param senderId The identifier to set for the sender in the chat.
     */
    private void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    /**
     * Getter for 'recipientId'.
     * @return The identifier of the recipient in the chat.
     */
    public String getRecipientId() {
        return recipientId;
    }

    /**
     * Setter for 'recipientId'.
     * @param recipientId The identifier to set for the recipient in the chat.
     */
    private void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    /**
     * Static method to create a ChatRoom instance with specified parameters.
     * @param chatId The identifier of the chat associated with this room.
     * @param senderId The identifier of the sender in the chat.
     * @param recipientId The identifier of the recipient in the chat.
     * @return A new ChatRoom instance with the given parameters set.
     */
    public static ChatRoom create(String chatId, String senderId, String recipientId){
        // Create a new ChatRoom instance
        ChatRoom chatRoom = new ChatRoom();

        // Set chatId, senderId, and recipientId using private setters
        chatRoom.setChatId(chatId);
        chatRoom.setSenderId(senderId);
        chatRoom.setRecipientId(recipientId);

        // Return the created ChatRoom instance
        return chatRoom;
    }
}
