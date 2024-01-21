package com.gameof3.gameof3artifact.Game;

import com.gameof3.gameof3artifact.ChatRoom.ChatRoomService;
import com.gameof3.gameof3artifact.chat.ChatMessageRepository;
import com.gameof3.gameof3artifact.chat.PlayerMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final PlayerModeRepository gameModeRepository;
    private final ChatRoomService chatRoomService;
    private final ChatMessageRepository chatMessageRepository;

    public Game markActive(Game game){
        game.setGameStatus("active");
        gameRepository.save(game);
        return game;
    }

    public Game markOver(Game game){
        game.setGameStatus("over");
        gameRepository.save(game);
        return game;
    }

    public Game findActiveGame(String chatId){
        List<Game> games = gameRepository.findByChatId(chatId);
        Game activeGame = null;
        for(Game game : games){
            if(game.getGameStatus().equals("active")){
                activeGame = game;
                break;
            }
        }
        return activeGame;
    }

    public void startNewGame(PlayerMessage playerMessage){
        Game game = new Game();
        game.setPlayer1(playerMessage.getSenderId());
        game.setPlayer2(playerMessage.getRecipientId());
        game.setChatId(playerMessage.getChatId());
        markActive(game);
    }

    public void completeGame(Game game){
        game.setGameStatus("over");
        markOver(game);
    }

    public PlayerMode setPlayerMode(PlayerMode playerMode){
        return gameModeRepository.save(playerMode);
    }

    public PlayerMode getPlayerMode(String user){
        PlayerMode playerMode = gameModeRepository.findByPlayer(user);
        return playerMode;
    }

}
