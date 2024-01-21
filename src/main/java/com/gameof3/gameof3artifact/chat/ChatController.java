package com.gameof3.gameof3artifact.chat;

import com.gameof3.gameof3artifact.ChatRoom.ChatRoomService;
import com.gameof3.gameof3artifact.Game.Game;
import com.gameof3.gameof3artifact.Game.GameService;
import com.gameof3.gameof3artifact.Game.PlayerMode;
import com.gameof3.gameof3artifact.clientmessage.ClientMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
/**
 * ChatController to handle messages between 2 players
 */
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final GameService gameService;
    private final ChatRoomService chatRoomService;
    private final ClientMessageService clientMessageService;

    /**
     * Method to process message passing and checks and balance on the overall flow, think of this like orchestrator
     * @param chatPlayerMessage
     */
    @MessageMapping("/chat")
    public void processMessage(@Payload PlayerMessage chatPlayerMessage) {
        int number = chatMessageService.processMessage(chatPlayerMessage);
        var chatId = chatRoomService.getOrCreateChatId(chatPlayerMessage.getSenderId(), chatPlayerMessage.getRecipientId());
        chatPlayerMessage.setChatId(chatId);
        Game game = gameService.findActiveGame(chatId);
        if(game==null){// no active game apply rule 1. can only send -1
           if(number==-1){
               gameService.startNewGame(chatPlayerMessage);
               clientMessageService.sendRegularMessage(chatPlayerMessage);//send -1 and save
               clientMessageService.sendGameStartedMessage(chatPlayerMessage);//send welcome and save
               String randomNumContent = clientMessageService.sendRandomNumberMessage(chatPlayerMessage);// send random and save
               String user = chatPlayerMessage.getRecipientId();
               PlayerMode playerMode = gameService.getPlayerMode(user);
               String playerModeString = playerMode.getPlayerMode();
               if(playerModeString.equals("auto")) {
                   clientMessageService.sendAutomaticMessage(chatPlayerMessage);//send auto message on behalf of recipient
                   chatPlayerMessage.setContent(randomNumContent);
                   clientMessageService.sendAutomaticNumberMessage(chatPlayerMessage);
               }
           }else{
               clientMessageService.sendInvalidNumberMessage(chatPlayerMessage);
           }
        }
        else{// active game apply rules - 1. who can send the message 2. what should be the next message
            boolean validSender = chatMessageService.isValidSender(chatPlayerMessage);
            if(validSender==false){
                clientMessageService.sendInvalidSenderMessage(chatPlayerMessage);
            }else{
                int lastNumber = chatMessageService.getLastNumber(chatPlayerMessage);
                int nextNum = clientMessageService.getNextMoveNum(lastNumber);

                if(number==nextNum){
                    if(number==1){
                       gameService.completeGame(game);// end the game
                       clientMessageService.sendLastMessage(chatPlayerMessage);
                       clientMessageService.sendGameResultMessage(chatPlayerMessage);
                    }
                    else {
                        String user = chatPlayerMessage.getRecipientId();
                        if(gameService.getPlayerMode(user).equals("auto")){
                            clientMessageService.sendAutomaticMessage(chatPlayerMessage);//send auto message on behalf of recipient
                            clientMessageService.sendAutomaticNumberMessage(chatPlayerMessage);
                        }else {
                            clientMessageService.sendRegularMessage(chatPlayerMessage);
                        }
                    }
                }else{
                    clientMessageService.sendRegularInvalidMessage(chatPlayerMessage);
                }
            }
        }
    }

    /**
     * Method to find previous chat messages when a player logs into the application, this method handles the offline user feature
     * @param senderId
     * @param recipientId
     * @return
     */
    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<PlayerMessage>> findChatMessages(@PathVariable String senderId,
                                                                @PathVariable String recipientId) {
        return ResponseEntity
                .ok(chatMessageService.findChatMessages(senderId, recipientId));
    }
}
