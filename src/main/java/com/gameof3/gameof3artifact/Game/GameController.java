package com.gameof3.gameof3artifact.Game;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
/*
Game controller hosting the endpoints of methods/APIs related
 */
public class GameController {

    private final GameService gameService;

    @PutMapping("/playerMode")
    public PlayerMode setPlayerMode(@RequestBody PlayerMode playerMode){
      return gameService.setPlayerMode(playerMode);
    }
}
