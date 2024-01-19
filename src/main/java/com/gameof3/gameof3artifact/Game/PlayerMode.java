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
public class PlayerMode {
    @Id
    private String id;

    @JsonProperty("player")
    private String player;

    @JsonProperty("playerMode")
    private String playerMode;
}
