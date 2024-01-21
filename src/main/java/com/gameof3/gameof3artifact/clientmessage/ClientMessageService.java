package com.gameof3.gameof3artifact.clientmessage;

import com.gameof3.gameof3artifact.chat.PlayerMessageService;
import com.gameof3.gameof3artifact.chat.ChatNotification;
import com.gameof3.gameof3artifact.chat.PlayerMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ClientMessageService {

    /**
     * PlayerMessageService dependency for handling player message related operations.
     */
    private final PlayerMessageService playerMessageService;

    /**
     * SimpMessagingTemplate dependency for sending WebSocket messages.
     */
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * setContent, setSenderId, setRecipientId, setTimestamp, setChatId
     * setContent, setSenderId, setRecipientId, setTimestamp, setChatId
     * setContent, setSenderId, setRecipientId, setTimestamp, setChatId
     */

    public void sendGameStartedMessage(PlayerMessage playerMessage){
        PlayerMessage gameOverPlayerMessage = new PlayerMessage();
        gameOverPlayerMessage.setContent("Game has started by "+ playerMessage.getSenderId());
        gameOverPlayerMessage.setSenderId(playerMessage.getSenderId());
        gameOverPlayerMessage.setRecipientId(playerMessage.getRecipientId());
        gameOverPlayerMessage.setTimestamp(new Date());
        gameOverPlayerMessage.setChatId(playerMessage.getChatId());
        PlayerMessage savedMsg = playerMessageService.saveNew(gameOverPlayerMessage);
        System.out.println("Message sent:"+ savedMsg.getContent());
        sendMessageToClient(savedMsg);
    }

    public void sendRandomNumberMessage(PlayerMessage playerMessage){
        Random rand = new Random();
        // Generate random integers in range 0 to 999
        int randomNumber = rand.nextInt(1000);
        PlayerMessage randomNumberMsg = new PlayerMessage();
        String randomNumContent = String.valueOf(randomNumber);
        randomNumberMsg.setContent(randomNumContent);
        randomNumberMsg.setSenderId(playerMessage.getSenderId());
        randomNumberMsg.setRecipientId(playerMessage.getRecipientId());
        randomNumberMsg.setTimestamp(new Date());
        randomNumberMsg.setChatId(playerMessage.getChatId());
        PlayerMessage savedMsg = playerMessageService.saveNew(randomNumberMsg);
        System.out.println("Message sent:"+ savedMsg.getContent());
        sendMessageToClient(savedMsg);
    }

    public void sendInvalidNumberMessage(PlayerMessage chatPlayerMessage){
        playerMessageService.saveNew(chatPlayerMessage);
        PlayerMessage newPlayerMessage = new PlayerMessage();
        newPlayerMessage.setContent("Please send -1 to start the game");
        newPlayerMessage.setSenderId(chatPlayerMessage.getRecipientId());// on behalf of recipient
        newPlayerMessage.setRecipientId(chatPlayerMessage.getSenderId());
        newPlayerMessage.setTimestamp(new Date());
        newPlayerMessage.setChatId(chatPlayerMessage.getChatId());
        PlayerMessage savedMsg = playerMessageService.saveNew(newPlayerMessage);//"Please send -1 to start the game"
        sendMessageToClient(savedMsg);
    }

    public void sendInvalidSenderMessage(PlayerMessage playerMessage){
        //chatMessageService.saveNew(chatMessage);
        PlayerMessage newPlayerMessage = new PlayerMessage();
        newPlayerMessage.setContent("Wait for my turn");
        newPlayerMessage.setSenderId(playerMessage.getRecipientId());// on behalf of recipient
        newPlayerMessage.setRecipientId(playerMessage.getSenderId());
        newPlayerMessage.setTimestamp(new Date());
        newPlayerMessage.setChatId(playerMessage.getChatId());
        //Message savedMsg = chatMessageService.saveNew(newMessage);//"Wait for my turn"
        sendMessageToClient(newPlayerMessage);
    }

    public void sendLastMessage(PlayerMessage playerMessage){
        PlayerMessage savedMsg = playerMessageService.saveNew(playerMessage);
        sendMessageToClient(savedMsg);
    }

    public void sendGameResultMessage(PlayerMessage chatPlayerMessage){
        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setContent("Game won by "+ chatPlayerMessage.getSenderId());
        playerMessage.setSenderId(chatPlayerMessage.getSenderId());
        playerMessage.setRecipientId(chatPlayerMessage.getRecipientId());
        playerMessage.setTimestamp(new Date());
        PlayerMessage gameOverPlayerMessage = playerMessageService.saveNew(playerMessage);//"Game over"
        sendMessageToClient(gameOverPlayerMessage);
    }

    public void sendRegularMessage(PlayerMessage playerMessage){
        PlayerMessage savedMsg = playerMessageService.saveNew(playerMessage);
        System.out.println("Message sent:"+ savedMsg.getContent());
        sendMessageToClient(savedMsg);
    }

    public void sendRegularInvalidMessage(PlayerMessage chatPlayerMessage, int playerNumber){
        PlayerMessage newPlayerMessage = new PlayerMessage();
        newPlayerMessage.setContent("Please send a valid number to continue the game. "+playerNumber+" is not a valid number");
        newPlayerMessage.setSenderId(chatPlayerMessage.getRecipientId());// on behalf of recipient
        newPlayerMessage.setRecipientId(chatPlayerMessage.getSenderId());
        newPlayerMessage.setTimestamp(new Date());
        // Message savedMsg = chatMessageService.saveNew(newMessage);//"Please send valid number to continue the game"
        sendMessageToClient(newPlayerMessage);
    }

    public void sendAutomaticMessage(PlayerMessage chatPlayerMessage) {
        PlayerMessage newPlayerMessage = new PlayerMessage();
        newPlayerMessage.setContent("Automated message from "+ chatPlayerMessage.getRecipientId());
        newPlayerMessage.setSenderId(chatPlayerMessage.getRecipientId());// on behalf of recipient
        newPlayerMessage.setRecipientId(chatPlayerMessage.getSenderId());
        newPlayerMessage.setTimestamp(new Date());
        PlayerMessage savedMsg = playerMessageService.saveNew(newPlayerMessage);//"Please send valid number to continue the game"
        sendMessageToClient(newPlayerMessage);
    }

    public void sendAutomaticNumberMessage(PlayerMessage playerMessage) {
        PlayerMessage newPlayerMessage = new PlayerMessage();
        int number = playerMessageService.processMessage(playerMessage);
        newPlayerMessage.setContent(String.valueOf(getNextMoveNum(number)));
        newPlayerMessage.setSenderId(playerMessage.getRecipientId());// on behalf of recipient
        newPlayerMessage.setRecipientId(playerMessage.getSenderId());
        newPlayerMessage.setTimestamp(new Date());
        PlayerMessage savedMsg = playerMessageService.saveNew(newPlayerMessage);//"auto num"
        sendMessageToClient(savedMsg);
    }

    public void sendRandomNumberMessage(PlayerMessage playerMessage, String randomNumberContent){
        PlayerMessage newPlayerMessage = new PlayerMessage();
        int number = playerMessageService.processMessage(playerMessage);
        newPlayerMessage.setContent(randomNumberContent);
        newPlayerMessage.setSenderId(playerMessage.getSenderId());
        newPlayerMessage.setRecipientId(playerMessage.getRecipientId());
        newPlayerMessage.setTimestamp(new Date());
        PlayerMessage savedMsg = playerMessageService.saveNew(newPlayerMessage);//"auto num"
        sendMessageToClient(savedMsg);
    }

    public int getNextMoveNum(int num){
        System.out.println("Server num received"+num);
        int remainder = num%3;
        int result = 0;
        if (remainder == 0) {
            result = num/3;
        } else if (remainder == 1) {
            result = (num - 1)/3;
        }else {
            result = (num + 1) / 3;
        }
        System.out.println("Server num sent"+result);
        return result;
    }

    private int sendMessageToClient(PlayerMessage savedMsg){
        messagingTemplate.convertAndSendToUser(
                savedMsg.getRecipientId(), "/queue/messages",
                new ChatNotification(
                        savedMsg.getId(),
                        savedMsg.getSenderId(),
                        savedMsg.getRecipientId(),
                        savedMsg.getContent()
                )
        );
        return 1;
    }
}