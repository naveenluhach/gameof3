package com.gameof3.gameof3artifact.Game;

import com.gameof3.gameof3artifact.chat.PlayerMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
/**
 * Service class for managing game-related operations.
 */
public class GameService {
    private final GameRepository gameRepository;
    private final PlayerModeRepository gameModeRepository;

    /**
     * Marks the provided game as active.
     * Updates the game status to "active" and saves it to the repository.
     * @param game The game to be marked as active
     * @return The updated game object
     */
    public Game markActive(Game game){
        game.setGameStatus("active");
        gameRepository.save(game);
        return game;
    }

    /**
     * Marks the provided game as over.
     * Updates the game status to "over" and saves it to the repository.
     * @param game The game to be marked as over
     * @return The updated game object
     */
    public Game markOver(Game game){
        game.setGameStatus("over");
        gameRepository.save(game);
        return game;
    }

    /**
     * Finds the active game associated with the provided chat ID.
     * @param chatId The chat ID to search for an active game
     * @return The active game object if found, otherwise null
     */
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

    /**
     * Starts a new game based on the information from the provided PlayerMessage.
     * Creates a new game, sets player information, and marks it as active.
     * @param playerMessage The PlayerMessage containing information to start a new game
     */
    public void startNewGame(PlayerMessage playerMessage){
        Game game = new Game();
        game.setPlayer1(playerMessage.getSenderId());
        game.setPlayer2(playerMessage.getRecipientId());
        game.setChatId(playerMessage.getChatId());
        markActive(game);
    }

    /**
     * Completes the provided game by marking it as over.
     * @param game The game to be marked as over
     */
    public void completeGame(Game game){
        game.setGameStatus("over");
        markOver(game);
    }

    /**
     * Sets the player mode using the provided PlayerMode object.
     * @param playerMode The PlayerMode object containing player mode information
     * @return The updated PlayerMode object
     */
    public PlayerMode setPlayerMode(PlayerMode playerMode){
        return gameModeRepository.save(playerMode);
    }

    /**
     * Retrieves the player mode for the specified user.
     * @param user The user for whom to retrieve the player mode
     * @return The PlayerMode object for the specified user
     */
    public PlayerMode getPlayerMode(String user){
        PlayerMode playerMode = gameModeRepository.findByPlayer(user);
        return playerMode;
    }
}
