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

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public String getChatId() {
        return chatId;
    }

    private void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getSenderId() {
        return senderId;
    }

    private void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    private void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getContent() {
        return content;
    }

    private void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    private void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public PlayerMessage updateChatId(PlayerMessage playerMessage, String chatId){
        playerMessage.setChatId(chatId);
        return playerMessage;
    }

    public static PlayerMessage create(String content, String senderId, String recipientId){
        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setContent(content);
        playerMessage.setSenderId(senderId);
        playerMessage.setRecipientId(recipientId);
        playerMessage.setTimestamp(new Date());
        return playerMessage;
    }



}
