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

    private final PlayerMessageService playerMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    public void sendGameStartedMessage(PlayerMessage chatPlayerMessage){
        PlayerMessage gameOverPlayerMessage = new PlayerMessage();
        gameOverPlayerMessage.setContent("Game has started by "+ chatPlayerMessage.getSenderId());
        gameOverPlayerMessage.setSenderId(chatPlayerMessage.getSenderId());
        gameOverPlayerMessage.setRecipientId(chatPlayerMessage.getRecipientId());
        gameOverPlayerMessage.setTimestamp(new Date());
        gameOverPlayerMessage.setChatId(chatPlayerMessage.getChatId());
        PlayerMessage savedMsg = playerMessageService.saveNew(gameOverPlayerMessage);
        sendMessageToClient(chatPlayerMessage, savedMsg);
    }

    public String sendRandomNumberMessage(PlayerMessage chatPlayerMessage){
        Random rand = new Random();
        // Generate random integers in range 0 to 999
        int randomNumber = rand.nextInt(1000);
        PlayerMessage randomNumberMsg = new PlayerMessage();
        String randomNumContent = String.valueOf(randomNumber);
        randomNumberMsg.setContent(randomNumContent);
        randomNumberMsg.setSenderId(chatPlayerMessage.getSenderId());
        randomNumberMsg.setRecipientId(chatPlayerMessage.getRecipientId());
        randomNumberMsg.setTimestamp(new Date());
        randomNumberMsg.setChatId(chatPlayerMessage.getChatId());
        PlayerMessage savedMsg = playerMessageService.saveNew(randomNumberMsg);
        sendMessageToClient(chatPlayerMessage, savedMsg);
        return randomNumContent;
    }

    public void sendInvalidNumberMessage(PlayerMessage chatPlayerMessage){
        playerMessageService.saveNew(chatPlayerMessage);
        PlayerMessage newPlayerMessage = new PlayerMessage();
        newPlayerMessage.setContent("Please send -1 to start the game");
        newPlayerMessage.setSenderId(chatPlayerMessage.getRecipientId());// on behalf of recipient
        newPlayerMessage.setRecipientId(chatPlayerMessage.getSenderId());
        newPlayerMessage.setChatId(chatPlayerMessage.getChatId());
        newPlayerMessage.setTimestamp(new Date());
        PlayerMessage savedMsg = playerMessageService.saveNew(newPlayerMessage);//"Please send -1 to start the game"
        sendMessageToClient(chatPlayerMessage, savedMsg);
    }

    public void sendInvalidSenderMessage(PlayerMessage chatPlayerMessage){
        //chatMessageService.saveNew(chatMessage);
        PlayerMessage newPlayerMessage = new PlayerMessage();
        newPlayerMessage.setContent("Wait for my turn");
        newPlayerMessage.setSenderId(chatPlayerMessage.getRecipientId());// on behalf of recipient
        newPlayerMessage.setRecipientId(chatPlayerMessage.getSenderId());
        newPlayerMessage.setTimestamp(new Date());
        newPlayerMessage.setChatId(chatPlayerMessage.getChatId());
        //Message savedMsg = chatMessageService.saveNew(newMessage);//"Wait for my turn"
        sendMessageToClient(chatPlayerMessage, newPlayerMessage);
    }

    public void sendLastMessage(PlayerMessage chatPlayerMessage){
        PlayerMessage savedMsg = playerMessageService.saveNew(chatPlayerMessage);
        sendMessageToClient(chatPlayerMessage, savedMsg);
    }

    public void sendGameResultMessage(PlayerMessage chatPlayerMessage){
        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setContent("Game won by "+ chatPlayerMessage.getSenderId());
        playerMessage.setSenderId(chatPlayerMessage.getSenderId());
        playerMessage.setRecipientId(chatPlayerMessage.getRecipientId());
        playerMessage.setTimestamp(new Date());
        PlayerMessage gameOverPlayerMessage = playerMessageService.saveNew(playerMessage);//"Game over"
        sendMessageToClient(chatPlayerMessage, gameOverPlayerMessage);
    }

    public void sendRegularMessage(PlayerMessage chatPlayerMessage){
        // set valid sender in Redis
        PlayerMessage savedMsg = playerMessageService.saveNew(chatPlayerMessage);
        sendMessageToClient(chatPlayerMessage, savedMsg);
    }

    public void sendRegularInvalidMessage(PlayerMessage chatPlayerMessage){
        PlayerMessage newPlayerMessage = new PlayerMessage();
        newPlayerMessage.setContent("Please send a valid number to continue the game");
        newPlayerMessage.setSenderId(chatPlayerMessage.getRecipientId());// on behalf of recipient
        newPlayerMessage.setRecipientId(chatPlayerMessage.getSenderId());
        newPlayerMessage.setTimestamp(new Date());
        // Message savedMsg = chatMessageService.saveNew(newMessage);//"Please send valid number to continue the game"
        sendMessageToClient(chatPlayerMessage, newPlayerMessage);
    }

    public void sendAutomaticMessage(PlayerMessage chatPlayerMessage) {
        PlayerMessage newPlayerMessage = new PlayerMessage();
        newPlayerMessage.setContent("Automated message from "+ chatPlayerMessage.getRecipientId());
        newPlayerMessage.setSenderId(chatPlayerMessage.getRecipientId());// on behalf of recipient
        newPlayerMessage.setRecipientId(chatPlayerMessage.getSenderId());
        newPlayerMessage.setTimestamp(new Date());
        PlayerMessage savedMsg = playerMessageService.saveNew(newPlayerMessage);//"Please send valid number to continue the game"
        sendMessageToClient(chatPlayerMessage, newPlayerMessage);
    }

    public void sendAutomaticNumberMessage(PlayerMessage chatPlayerMessage) {
        PlayerMessage newPlayerMessage = new PlayerMessage();
        int number = playerMessageService.processMessage(chatPlayerMessage);
        newPlayerMessage.setContent(String.valueOf(getNextMoveNum(number)));
        newPlayerMessage.setSenderId(chatPlayerMessage.getRecipientId());// on behalf of recipient
        newPlayerMessage.setRecipientId(chatPlayerMessage.getSenderId());
        newPlayerMessage.setTimestamp(new Date());
        PlayerMessage savedMsg = playerMessageService.saveNew(newPlayerMessage);//"auto num"
        sendMessageToClient(chatPlayerMessage, savedMsg);
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
    /*
    async function getNextMoveNum(num) {
    const remainder = num % 3;

    if (remainder === 0) {
        return num / 3;
    } else if (remainder === 1) {
        return (num - 1) / 3;
    } else { // remainder === 2
        return (num + 1) / 3;
    }
}
     */

    private int sendMessageToClient(PlayerMessage chatPlayerMessage, PlayerMessage savedMsg){
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