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
     * Identifier for the game associated with this message.
     */
    private String gameId;
}
