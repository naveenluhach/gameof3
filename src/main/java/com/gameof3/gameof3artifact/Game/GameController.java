package com.gameof3.gameof3artifact.Game;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
/**
 * Controller class for managing game-related operations.
 */
public class GameController {

    // GameService dependency for handling game-related operations
    private final GameService gameService;

    /**
     * Sets the player mode using the provided PlayerMode object.
     * Calls the setPlayerMode method of the GameService to handle the operation.
     * @param playerMode The PlayerMode object containing player mode information
     * @return The updated PlayerMode object
     */
    @PutMapping("/playerMode")
    public PlayerMode setPlayerMode(@RequestBody PlayerMode playerMode){
      return gameService.setPlayerMode(playerMode);
    }
}
