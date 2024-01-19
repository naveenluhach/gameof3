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
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final GameService gameService;
    private final ChatRoomService chatRoomService;
    private final ClientMessageService clientMessageService;

    @MessageMapping("/chat")
    public void processMessage(@Payload Message chatMessage) {
        int number = chatMessageService.processMessage(chatMessage);
        var chatId = chatRoomService.getOrCreateChatId(chatMessage.getSenderId(), chatMessage.getRecipientId());
        chatMessage.setChatId(chatId);
        Game game = gameService.findActiveGame(chatId);
        if(game==null){// no active game apply rule 1. can only send -1
           if(number==-1){
               gameService.startNewGame(chatMessage);
               clientMessageService.sendRegularMessage(chatMessage);//send -1 and save
               clientMessageService.sendGameStartedMessage(chatMessage);//send welcome and save
               String randomNumContent = clientMessageService.sendRandomNumberMessage(chatMessage);// send random and save
               String user = chatMessage.getRecipientId();
               PlayerMode playerMode = gameService.getPlayerMode(user);
               String playerModeString = playerMode.getPlayerMode();
               if(playerModeString.equals("auto")) {
                   clientMessageService.sendAutomaticMessage(chatMessage);//send auto message on behalf of recipient
                   chatMessage.setContent(randomNumContent);
                   clientMessageService.sendAutomaticNumberMessage(chatMessage);
               }
           }else{
               clientMessageService.sendInvalidNumberMessage(chatMessage);
           }
        }
        else{// active game apply rules - 1. who can send the message 2. what should be the next message
            boolean validSender = chatMessageService.isValidSender(chatMessage);
            if(validSender==false){
                clientMessageService.sendInvalidSenderMessage(chatMessage);
            }else{
                int lastNumber = chatMessageService.getLastNumber(chatMessage);
                int nextNum = clientMessageService.getNextMoveNum(lastNumber);

                if(number==nextNum){
                    if(number==1){
                       gameService.completeGame(game);// end the game
                       clientMessageService.sendLastMessage(chatMessage);
                       clientMessageService.sendGameResultMessage(chatMessage);
                    }
                    else {
                        String user = chatMessage.getRecipientId();
                        if(gameService.getPlayerMode(user).equals("auto")){
                            clientMessageService.sendAutomaticMessage(chatMessage);//send auto message on behalf of recipient
                            clientMessageService.sendAutomaticNumberMessage(chatMessage);
                        }else {
                            clientMessageService.sendRegularMessage(chatMessage);
                        }
                    }
                }else{
                    clientMessageService.sendRegularInvalidMessage(chatMessage);
                }
            }
        }
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<Message>> findChatMessages(@PathVariable String senderId,
                                                              @PathVariable String recipientId) {
        return ResponseEntity
                .ok(chatMessageService.findChatMessages(senderId, recipientId));
    }
}
