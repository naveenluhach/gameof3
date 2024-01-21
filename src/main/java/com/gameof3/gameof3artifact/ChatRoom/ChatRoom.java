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

    public static ChatRoom create(String chatId, String senderId, String recipientId){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setChatId(chatId);
        chatRoom.setSenderId(senderId);
        chatRoom.setRecipientId(recipientId);
        return chatRoom;
    }
}
