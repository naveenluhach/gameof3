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
     * Identifier for the chat associated with this game.
     */
    private String chatId;

    /**
     * Getter for 'id'.
     * @return The unique identifier of the game.
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for 'id'.
     * @param id The unique identifier to set for the game.
     */
    private void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for 'gameStatus'.
     * @return The current status of the game.
     */
    public String getGameStatus() {
        return gameStatus;
    }

    /**
     * Setter for 'gameStatus'.
     * @param gameStatus The status to set for the game.
     */
    private void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    /**
     * Getter for 'player1'.
     * @return The identifier of player 1 in the game.
     */
    public String getPlayer1() {
        return player1;
    }

    /**
     * Setter for 'player1'.
     * @param player1 The identifier to set for player 1 in the game.
     */
    private void setPlayer1(String player1) {
        this.player1 = player1;
    }

    /**
     * Getter for 'player2'.
     * @return The identifier of player 2 in the game.
     */
    public String getPlayer2() {
        return player2;
    }

    /**
     * Setter for 'player2'.
     * @param player2 The identifier to set for player 2 in the game.
     */
    private void setPlayer2(String player2) {
        this.player2 = player2;
    }

    /**
     * Getter for 'chatId'.
     * @return The identifier of the chat associated with this game.
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
     * Static method to create a Game instance with specified parameters.
     * @param player1 The identifier of player 1 in the game.
     * @param player2 The identifier of player 2 in the game.
     * @param chatId The identifier of the chat associated with this game.
     * @return A new Game instance with the given parameters set.
     */
    public static Game create(String player1, String player2, String chatId){
        // Create a new Game instance
        Game game = new Game();

        // Set player1, player2, and chatId using private setters
        game.setPlayer1(player1);
        game.setPlayer2(player2);
        game.setChatId(chatId);

        // Return the created Game instance
        return game;
    }

    /**
     * Static method to update the status of an existing Game instance.
     * @param game The Game instance to update.
     * @param gameStatus The new status to set for the game.
     * @return The updated Game instance.
     */
    public static Game updateStatus(Game game, String gameStatus){
        // Update the gameStatus using the setter
        game.setGameStatus(gameStatus);

        // Return the updated Game instance
        return game;
    }
}
