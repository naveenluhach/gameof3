package com.gameof3.gameof3artifact.Game;

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
@Document(collection = "game")
/**
 * Represents a game entity in the application.
 * This class stores information about a game, including its status, players, modes, and associated chat.
 */
public class Game {
    /**
     * Unique identifier for the game.
     */
    @Id
    private String id;

    /**
     * Status of the game (e.g., "active", "over").
     */
    private String gameStatus;

    /**
     * User ID of player 1.
     */
    private String player1;

    /**
     * User ID of player 2.
     */
    private String player2;

    /**
     * Mode of player 1 in the game.
     */
    private String player1Mode;

    /**
     * Mode of player 2 in the game.
     */
    private String player2Mode;

    /**
     * Identifier for the chat associated with this game.
     */
    private String chatId;
}
