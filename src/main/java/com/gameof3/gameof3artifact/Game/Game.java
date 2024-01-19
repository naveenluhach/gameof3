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
/*
Game class describing the properties of a game between 2 players
 */
public class Game {
    @Id
    private String id;

    private String gameStatus;
    private String player1;
    private String player2;
    private String player1Mode;
    private String player2Mode;
    private String chatId;
}
