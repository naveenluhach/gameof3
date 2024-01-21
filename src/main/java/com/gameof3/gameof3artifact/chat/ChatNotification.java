package com.gameof3.gameof3artifact.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * Represents a notification sent within the chat.
 * This class stores information about the sender, recipient, and content of the notification.
 */
public class ChatNotification {

    /**
     * Unique identifier for the chat notification
     */
    private String id;

    /**
     * User ID of the sender
     */
    private String senderId;

    /**
     * User ID of the recipient
     */
    private String recipientId;

    /**
     * Content of the notification
     */
    private String content;
}
