package com.gameof3.gameof3artifact.Game;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(GameController.class);

    /**
     * Sets the player mode using the provided PlayerMode object.
     * Calls the setPlayerMode method of the GameService to handle the operation.
     * @param playerMode The PlayerMode object containing player mode information
     * @return The updated PlayerMode object
     */
    @PutMapping("/playerMode")
    public PlayerMode setPlayerMode(@RequestBody PlayerMode playerMode){
        logger.info("Request received to find player mode for : "+playerMode.toString());
      return gameService.setPlayerMode(playerMode);
    }
}
