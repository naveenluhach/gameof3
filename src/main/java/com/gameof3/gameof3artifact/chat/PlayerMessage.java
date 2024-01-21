package com.gameof3.gameof3artifact.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "player_message")
/**
 * Represents a player message entity in the application.
 * This class stores information about messages exchanged between players in a chat.
 */
public class PlayerMessage {
    /**
     * Unique identifier for the player message.
     */
    @Id
    private String id;

    /**
     * Identifier for the chat associated with this message.
     */
    private String chatId;

    /**
     * User ID of the message sender.
     */
    private String senderId;

    /**
     * User ID of the message recipient.
     */
    private String recipientId;

    /**
     * Content of the player message.
     */
    private String content;

    /**
     * Timestamp indicating when the message was sent.
     */
    private Date timestamp;

    /**
     * Getter for 'id'.
     * @return The unique identifier of the player message.
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for 'id'.
     * @param id The unique identifier to set for the player message.
     */
    private void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for 'chatId'.
     * @return The identifier of the chat associated with this message.
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
     * @return The identifier of the sender of the message.
     */
    public String getSenderId() {
        return senderId;
    }

    /**
     * Setter for 'senderId'.
     * @param senderId The identifier to set for the sender of the message.
     */
    private void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    /**
     * Getter for 'recipientId'.
     * @return The identifier of the recipient of the message.
     */
    public String getRecipientId() {
        return recipientId;
    }

    /**
     * Setter for 'recipientId'.
     * @param recipientId The identifier to set for the recipient of the message.
     */
    private void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    /**
     * Getter for 'content'.
     * @return The content of the player message.
     */
    public String getContent() {
        return content;
    }

    /**
     * Setter for 'content'.
     * @param content The content to set for the player message.
     */
    private void setContent(String content) {
        this.content = content;
    }

    /**
     * Getter for 'timestamp'.
     * @return The timestamp of when the player message was created.
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Setter for 'timestamp'.
     * @param timestamp The timestamp to set for when the player message was created.
     */
    private void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Static method to update the chatId of an existing PlayerMessage instance.
     * @param playerMessage The PlayerMessage instance to update.
     * @param chatId The new chatId to set for the player message.
     * @return The updated PlayerMessage instance.
     */
    public PlayerMessage updateChatId(PlayerMessage playerMessage, String chatId){
        // Update the chatId using the setter
        playerMessage.setChatId(chatId);

        // Return the updated PlayerMessage instance
        return playerMessage;
    }

    /**
     * Static method to create a PlayerMessage instance with specified parameters.
     * @param content The content of the player message.
     * @param senderId The identifier of the sender of the message.
     * @param recipientId The identifier of the recipient of the message.
     * @return A new PlayerMessage instance with the given parameters set.
     */
    public static PlayerMessage create(String content, String senderId, String recipientId){
        // Create a new PlayerMessage instance
        PlayerMessage playerMessage = new PlayerMessage();

        // Set content, senderId, recipientId, and timestamp using private setters
        playerMessage.setContent(content);
        playerMessage.setSenderId(senderId);
        playerMessage.setRecipientId(recipientId);
        playerMessage.setTimestamp(new Date());

        // Return the created PlayerMessage instance
        return playerMessage;
    }
}
