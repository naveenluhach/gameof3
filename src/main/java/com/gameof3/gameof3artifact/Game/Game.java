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

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    private void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getPlayer1() {
        return player1;
    }

    private void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    private void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getChatId() {
        return chatId;
    }

    private void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public static Game create(String player1, String player2, String chatId){
        Game game = new Game();
        game.setPlayer1(player1);
        game.setPlayer2(player2);
        game.setChatId(chatId);
        return game;
    }

    public static Game updateStatus(Game game, String gameStatus){
        game.setGameStatus(gameStatus);
        return game;
    }
}
