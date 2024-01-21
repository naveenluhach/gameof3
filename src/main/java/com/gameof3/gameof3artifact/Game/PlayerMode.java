package com.gameof3.gameof3artifact.Game;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Document(collection = "player_mode")
/**
 * Represents a player mode entity in the application.
 * This class stores information about the mode of a player, including the player's ID and mode.
 */
public class PlayerMode {

    /**
     * Unique identifier for the player mode.
     */
    @Id
    private String id;

    /**
     * User ID of the player associated with this mode.
     */
    @JsonProperty("player")
    private String player;

    /**
     * The mode of the player (e.g., "auto", "man").
     */
    @JsonProperty("playerMode")
    private String playerMode;
}
