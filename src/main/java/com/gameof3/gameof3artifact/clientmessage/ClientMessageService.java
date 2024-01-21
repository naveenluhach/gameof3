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

    public void sendGameStartedMessage(PlayerMessage playerMessage){
        String content = "Game has started by "+ playerMessage.getSenderId();
        PlayerMessage messageToSend = getMessageToSend(content, playerMessage.getSenderId(), playerMessage.getRecipientId(), playerMessage.getChatId());
        PlayerMessage persistedMessage = playerMessageService.save(messageToSend);
        sendMessageToClient(persistedMessage);
    }

    public void sendRandomNumberMessage(PlayerMessage playerMessage){
        int randomNumber = generateRandomNumber();
        String randomNumContent = getRandomNumContent(randomNumber);
        PlayerMessage messageToSend = getMessageToSend(randomNumContent, playerMessage.getSenderId(), playerMessage.getRecipientId(), playerMessage.getChatId());
        PlayerMessage persistedMessage = playerMessageService.save(messageToSend);
        sendMessageToClient(persistedMessage);
    }

    public void sendInvalidNumberMessage(PlayerMessage chatPlayerMessage){
        playerMessageService.save(chatPlayerMessage);
        String content = "Please send -1 to start the game";
        PlayerMessage messageToSend = getMessageToSend(content, chatPlayerMessage.getRecipientId(), chatPlayerMessage.getSenderId(), chatPlayerMessage.getChatId());
        PlayerMessage persistedMessage = playerMessageService.save(messageToSend);//"Please send -1 to start the game"
        sendMessageToClient(persistedMessage);
    }

    public void sendInvalidSenderMessage(PlayerMessage playerMessage){
        // We do not persist it because it will make the last message number logic complex for now
        // chatMessageService.saveNew(chatMessage);
        String content =  "Wait for my turn";
        PlayerMessage messageToSend = getMessageToSend(content, playerMessage.getRecipientId(), playerMessage.getSenderId(), playerMessage.getChatId());
        sendMessageToClient(messageToSend);// only send
    }

    public void sendGameResultMessage(PlayerMessage chatPlayerMessage){
        String content = "Game won by "+ chatPlayerMessage.getSenderId();
        PlayerMessage messageToSend = getMessageToSendWOChatId(content, chatPlayerMessage.getSenderId(), chatPlayerMessage.getRecipientId());
        PlayerMessage gameOverPlayerMessage = playerMessageService.save(messageToSend);
        sendMessageToClient(gameOverPlayerMessage);// save and send
    }

    public void sendRegularMessage(PlayerMessage playerMessage){
        PlayerMessage savedMsg = playerMessageService.save(playerMessage);
        sendMessageToClient(savedMsg);// only send
    }

    public void sendRegularInvalidMessage(PlayerMessage chatPlayerMessage, int playerNumber){
        String content = "Please send a valid number to continue the game. "+playerNumber+" is not a valid";
        PlayerMessage messageToSend = getMessageToSendWOChatId(content, chatPlayerMessage.getRecipientId(), chatPlayerMessage.getSenderId());
        // Message savedMsg = chatMessageService.saveNew(newMessage);//"Please send valid number to continue the game"
        // only send to client and do not save in DB
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

    private PlayerMessage getMessageToSend(String content, String senderId, String recipientId, String chatId){
        PlayerMessage messageToSend = new PlayerMessage();
        messageToSend.setContent(content);
        messageToSend.setSenderId(senderId);
        messageToSend.setRecipientId(recipientId);
        messageToSend.setTimestamp(new Date());
        messageToSend.setChatId(chatId);
        return messageToSend;
    }

    private PlayerMessage getMessageToSendWOChatId(String content, String senderId, String recipientId){
        PlayerMessage messageToSend = new PlayerMessage();
        messageToSend.setContent(content);
        messageToSend.setSenderId(senderId);
        messageToSend.setRecipientId(recipientId);
        messageToSend.setTimestamp(new Date());
        return messageToSend;
    }
}