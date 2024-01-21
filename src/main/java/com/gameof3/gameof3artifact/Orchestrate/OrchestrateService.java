package com.gameof3.gameof3artifact.Orchestrate;

import com.gameof3.gameof3artifact.ChatRoom.ChatRoomService;
import com.gameof3.gameof3artifact.Game.Game;
import com.gameof3.gameof3artifact.Game.GameService;
import com.gameof3.gameof3artifact.Game.PlayerMode;
import com.gameof3.gameof3artifact.chat.PlayerMessageService;
import com.gameof3.gameof3artifact.chat.PlayerMessage;
import com.gameof3.gameof3artifact.clientmessage.ClientMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
/**
 * Orchestrate service to talk to different services and coordinate between them
 */
public class OrchestrateService {

    private final PlayerMessageService playerMessageService;
    private final GameService gameService;
    private final ChatRoomService chatRoomService;
    private final ClientMessageService clientMessageService;

    /**
     * Method to process message between 2 players
     * @param playerMessage
     */
    public void processPlayerMessage(PlayerMessage playerMessage) {
        int number = playerMessageService.processMessage(playerMessage);
        var chatId = chatRoomService.getOrCreateChatId(playerMessage.getSenderId(), playerMessage.getRecipientId());
        playerMessage.setChatId(chatId);
        Game game = gameService.findActiveGame(chatId);

        // Check if no active game
        if(game==null){
            // Rule check: Fist number can only be -1
            if(number==-1){
                gameService.startNewGame(playerMessage);
                clientMessageService.sendRegularMessage(playerMessage);
                // Send welcome message
                clientMessageService.sendGameStartedMessage(playerMessage);

                // Send random number
                String randomNumContent = clientMessageService.sendRandomNumberMessage(playerMessage);
                String user = playerMessage.getRecipientId();
                PlayerMode playerMode = gameService.getPlayerMode(user);
                String playerModeString = playerMode.getPlayerMode();

                // Check if automatic reply is enabled
                if(playerModeString.equals("auto")) {
                    clientMessageService.sendAutomaticMessage(playerMessage);
                    playerMessage.setContent(randomNumContent);
                    clientMessageService.sendAutomaticNumberMessage(playerMessage);
                }
            }else{
                clientMessageService.sendInvalidNumberMessage(playerMessage);
            }
        }
        // If active game is already present
        else{
            boolean validSender = playerMessageService.isValidSender(playerMessage);
            // Rule number 1 check : Who can send the message
            if(validSender==false){
                clientMessageService.sendInvalidSenderMessage(playerMessage);
            }else{
                int lastNumber = playerMessageService.getLastNumber(playerMessage);
                int nextNum = clientMessageService.getNextMoveNum(lastNumber);

                // Rule number 2 check: What should be the next number
                if(number==nextNum){
                    if(number==1){
                        // End the game
                        gameService.completeGame(game);
                        clientMessageService.sendLastMessage(playerMessage);
                        clientMessageService.sendGameResultMessage(playerMessage);
                    }
                    else {
                        String user = playerMessage.getRecipientId();
                        // Check if automatic reply is enabled
                        if(gameService.getPlayerMode(user).equals("auto")){
                            clientMessageService.sendAutomaticMessage(playerMessage);
                            clientMessageService.sendAutomaticNumberMessage(playerMessage);
                        }else {
                            clientMessageService.sendRegularMessage(playerMessage);
                        }
                    }
                }else{
                    clientMessageService.sendRegularInvalidMessage(playerMessage);
                }
            }
        }
    }
}
