package com.gameof3.gameof3artifact.clientmessage;

import com.gameof3.gameof3artifact.chat.PlayerMessageService;
import com.gameof3.gameof3artifact.chat.ChatNotification;
import com.gameof3.gameof3artifact.chat.PlayerMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(ClientMessageService.class);

    /**
     * Sends a game started message to the specified player.
     * @param playerMessage The player message triggering the game started event.
     */
    public void sendGameStartedMessage(PlayerMessage playerMessage){
        String content = "Game has started by "+ playerMessage.getSenderId();
        PlayerMessage messageToSend = getMessageToSend(content, playerMessage.getSenderId(), playerMessage.getRecipientId(), playerMessage.getChatId());
        PlayerMessage persistedMessage = playerMessageService.save(messageToSend);
        sendMessageToClient(persistedMessage);
    }

    /**
     * Sends a message containing a randomly generated number to the specified player.
     * @param playerMessage The player message triggering the random number event.
     */
    public void sendRandomNumberMessage(PlayerMessage playerMessage){
        int randomNumber = generateRandomNumber();
        String randomNumContent = getRandomNumContent(randomNumber);
        PlayerMessage messageToSend = getMessageToSend(randomNumContent, playerMessage.getSenderId(), playerMessage.getRecipientId(), playerMessage.getChatId());
        PlayerMessage persistedMessage = playerMessageService.save(messageToSend);
        sendMessageToClient(persistedMessage);
    }

    /**
     * Sends a message notifying the player about an invalid number.
     * @param chatPlayerMessage The player message with an invalid number.
     */
    public void sendInvalidNumberMessage(PlayerMessage chatPlayerMessage){
        playerMessageService.save(chatPlayerMessage);
        String content = "Please send -1 to start the game";
        PlayerMessage messageToSend = getMessageToSend(content, chatPlayerMessage.getRecipientId(), chatPlayerMessage.getSenderId(), chatPlayerMessage.getChatId());
        PlayerMessage persistedMessage = playerMessageService.save(messageToSend);//"Please send -1 to start the game"
        sendMessageToClient(persistedMessage);
    }

    /**
     * Sends a message notifying the player about an invalid sender.
     * @param playerMessage The player message with an invalid sender.
     */
    public void sendInvalidSenderMessage(PlayerMessage playerMessage){
        // We do not persist it because it will make the last message number logic complex for now
        // chatMessageService.saveNew(chatMessage);
        String content =  "Wait for my turn";
        PlayerMessage messageToSend = getMessageToSend(content, playerMessage.getRecipientId(), playerMessage.getSenderId(), playerMessage.getChatId());
        sendMessageToClient(messageToSend);// only send
    }

    /**
     * Sends a message notifying the players about the game result.
     * @param chatPlayerMessage The player message containing the game result.
     */
    public void sendGameResultMessage(PlayerMessage chatPlayerMessage){
        String content = "Game won by "+ chatPlayerMessage.getSenderId();
        PlayerMessage messageToSend = getMessageToSendWOChatId(content, chatPlayerMessage.getSenderId(), chatPlayerMessage.getRecipientId());
        PlayerMessage gameOverPlayerMessage = playerMessageService.save(messageToSend);
        sendMessageToClient(gameOverPlayerMessage);// save and send
    }

    /**
     * Sends a regular message to the specified player.
     * @param playerMessage The regular player message to be sent.
     */
    public void sendRegularMessage(PlayerMessage playerMessage){
        PlayerMessage savedMsg = playerMessageService.save(playerMessage);
        sendMessageToClient(savedMsg);// only send
    }

    /**
     * Sends a regular invalid message to the specified player.
     * @param chatPlayerMessage The player message triggering the invalid message event.
     * @param playerNumber The invalid player number.
     */
    public void sendRegularInvalidMessage(PlayerMessage chatPlayerMessage, int playerNumber){
        String content = "Please send a valid number to continue the game. "+playerNumber+" is not a valid";
        PlayerMessage messageToSend = getMessageToSendWOChatId(content, chatPlayerMessage.getRecipientId(), chatPlayerMessage.getSenderId());
        // Message savedMsg = chatMessageService.saveNew(newMessage);//"Please send valid number to continue the game"
        // only send to client and do not save in DB
        sendMessageToClient(messageToSend);
    }

    /**
     * Sends a message to the client using WebSocket.
     * @param savedMsg The player message to be sent to the client.
     * @return An integer indicating the success of the operation.
     */
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

    /**
     * Generates a random number between 0 and 999.
     * @return The randomly generated number.
     */
    private int generateRandomNumber(){
        Random rand = new Random();
        // Generate random integers in range 0 to 999
        int randomNumber = rand.nextInt(1000);
        return randomNumber;
    }

    /**
     * Generates the content for a message containing a random number.
     * @param randomNumber The randomly generated number.
     * @return The content for the message.
     */
    private String getRandomNumContent(int randomNumber){
        return String.valueOf(randomNumber);
    }

    /**
     * Creates a PlayerMessage with the specified content and details.
     * @param content The content of the message.
     * @param senderId The ID of the message sender.
     * @param recipientId The ID of the message recipient.
     * @param chatId The ID of the chat.
     * @return The created PlayerMessage.
     */
    private PlayerMessage getMessageToSend(String content, String senderId, String recipientId, String chatId){
        PlayerMessage messageToSend = new PlayerMessage();
        messageToSend.setContent(content);
        messageToSend.setSenderId(senderId);
        messageToSend.setRecipientId(recipientId);
        messageToSend.setTimestamp(new Date());
        messageToSend.setChatId(chatId);
        return messageToSend;
    }

    /**
     * Creates a PlayerMessage without a chat ID.
     * @param content The content of the message.
     * @param senderId The ID of the message sender.
     * @param recipientId The ID of the message recipient.
     * @return The created PlayerMessage.
     */
    private PlayerMessage getMessageToSendWOChatId(String content, String senderId, String recipientId){
        PlayerMessage messageToSend = new PlayerMessage();
        messageToSend.setContent(content);
        messageToSend.setSenderId(senderId);
        messageToSend.setRecipientId(recipientId);
        messageToSend.setTimestamp(new Date());
        return messageToSend;
    }
}