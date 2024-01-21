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
public class OrchestrateService {

    private final PlayerMessageService playerMessageService;
    private final GameService gameService;
    private final ChatRoomService chatRoomService;
    private final ClientMessageService clientMessageService;

    public void processPlayerMessage(PlayerMessage playerMessage) {
        int number = playerMessageService.processMessage(playerMessage);
        var chatId = chatRoomService.getOrCreateChatId(playerMessage.getSenderId(), playerMessage.getRecipientId());
        playerMessage.setChatId(chatId);
        Game game = gameService.findActiveGame(chatId);
        if(game==null){// no active game apply rule 1. can only send -1
            if(number==-1){
                gameService.startNewGame(playerMessage);
                clientMessageService.sendRegularMessage(playerMessage);//send -1 and save
                clientMessageService.sendGameStartedMessage(playerMessage);//send welcome and save
                String randomNumContent = clientMessageService.sendRandomNumberMessage(playerMessage);// send random and save
                String user = playerMessage.getRecipientId();
                PlayerMode playerMode = gameService.getPlayerMode(user);
                String playerModeString = playerMode.getPlayerMode();
                if(playerModeString.equals("auto")) {
                    clientMessageService.sendAutomaticMessage(playerMessage);//send auto message on behalf of recipient
                    playerMessage.setContent(randomNumContent);
                    clientMessageService.sendAutomaticNumberMessage(playerMessage);
                }
            }else{
                clientMessageService.sendInvalidNumberMessage(playerMessage);
            }
        }
        else{// active game apply rules - 1. who can send the message 2. what should be the next message
            boolean validSender = playerMessageService.isValidSender(playerMessage);
            if(validSender==false){
                clientMessageService.sendInvalidSenderMessage(playerMessage);
            }else{
                int lastNumber = playerMessageService.getLastNumber(playerMessage);
                int nextNum = clientMessageService.getNextMoveNum(lastNumber);

                if(number==nextNum){
                    if(number==1){
                        gameService.completeGame(game);// end the game
                        clientMessageService.sendLastMessage(playerMessage);
                        clientMessageService.sendGameResultMessage(playerMessage);
                    }
                    else {
                        String user = playerMessage.getRecipientId();
                        if(gameService.getPlayerMode(user).equals("auto")){
                            clientMessageService.sendAutomaticMessage(playerMessage);//send auto message on behalf of recipient
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
