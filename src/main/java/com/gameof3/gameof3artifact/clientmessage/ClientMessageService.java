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
        PlayerMessage messageToSend = new PlayerMessage();
        messageToSend.setContent("Game has started by "+ playerMessage.getSenderId());
        messageToSend.setSenderId(playerMessage.getSenderId());
        messageToSend.setRecipientId(playerMessage.getRecipientId());
        messageToSend.setTimestamp(new Date());
        messageToSend.setChatId(playerMessage.getChatId());
        PlayerMessage persistedMessage = playerMessageService.save(messageToSend);
        sendMessageToClient(persistedMessage);
    }

    public void sendRandomNumberMessage(PlayerMessage playerMessage){
        int randomNumber = generateRandomNumber();
        String randomNumContent = getRandomNumContent(randomNumber);
        PlayerMessage messageToSend = new PlayerMessage();
        messageToSend.setContent(randomNumContent);
        messageToSend.setSenderId(playerMessage.getSenderId());
        messageToSend.setRecipientId(playerMessage.getRecipientId());
        messageToSend.setTimestamp(new Date());
        messageToSend.setChatId(playerMessage.getChatId());
        PlayerMessage persistedMessage = playerMessageService.save(messageToSend);
        sendMessageToClient(persistedMessage);
    }

    public void sendInvalidNumberMessage(PlayerMessage chatPlayerMessage){
        playerMessageService.save(chatPlayerMessage);
        PlayerMessage messageToSend = new PlayerMessage();
        messageToSend.setContent("Please send -1 to start the game");
        messageToSend.setSenderId(chatPlayerMessage.getRecipientId());// on behalf of recipient
        messageToSend.setRecipientId(chatPlayerMessage.getSenderId());
        messageToSend.setTimestamp(new Date());
        messageToSend.setChatId(chatPlayerMessage.getChatId());
        PlayerMessage persistedMessage = playerMessageService.save(messageToSend);//"Please send -1 to start the game"
        sendMessageToClient(persistedMessage);
    }

    public void sendInvalidSenderMessage(PlayerMessage playerMessage){
        //chatMessageService.saveNew(chatMessage);
        PlayerMessage messageToSend = new PlayerMessage();
        messageToSend.setContent("Wait for my turn");
        messageToSend.setSenderId(playerMessage.getRecipientId());// on behalf of recipient
        messageToSend.setRecipientId(playerMessage.getSenderId());
        messageToSend.setTimestamp(new Date());
        messageToSend.setChatId(playerMessage.getChatId());
        sendMessageToClient(messageToSend);
    }

    public void sendLastMessage(PlayerMessage playerMessage){
        PlayerMessage savedMsg = playerMessageService.save(playerMessage);
        sendMessageToClient(savedMsg);
    }

    public void sendGameResultMessage(PlayerMessage chatPlayerMessage){
        PlayerMessage messageToSend = new PlayerMessage();
        messageToSend.setContent("Game won by "+ chatPlayerMessage.getSenderId());
        messageToSend.setSenderId(chatPlayerMessage.getSenderId());
        messageToSend.setRecipientId(chatPlayerMessage.getRecipientId());
        messageToSend.setTimestamp(new Date());
        PlayerMessage gameOverPlayerMessage = playerMessageService.save(messageToSend);//"Game over"
        sendMessageToClient(gameOverPlayerMessage);
    }

    public void sendRegularMessage(PlayerMessage playerMessage){
        PlayerMessage savedMsg = playerMessageService.save(playerMessage);
        System.out.println("Message sent:"+ savedMsg.getContent());
        sendMessageToClient(savedMsg);
    }

    public void sendRegularInvalidMessage(PlayerMessage chatPlayerMessage, int playerNumber){
        PlayerMessage messageToSend = new PlayerMessage();
        messageToSend.setContent("Please send a valid number to continue the game. "+playerNumber+" is not a valid number");
        messageToSend.setSenderId(chatPlayerMessage.getRecipientId());// on behalf of recipient
        messageToSend.setRecipientId(chatPlayerMessage.getSenderId());
        messageToSend.setTimestamp(new Date());
        // Message savedMsg = chatMessageService.saveNew(newMessage);//"Please send valid number to continue the game"
        sendMessageToClient(messageToSend);
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

    private int generateRandomNumber(){
        Random rand = new Random();
        // Generate random integers in range 0 to 999
        int randomNumber = rand.nextInt(1000);
        return randomNumber;
    }

    private String getRandomNumContent(int randomNumber){
        return String.valueOf(randomNumber);
    }
}