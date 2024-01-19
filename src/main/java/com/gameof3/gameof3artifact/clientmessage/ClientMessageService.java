package com.gameof3.gameof3artifact.clientmessage;

import com.gameof3.gameof3artifact.chat.ChatMessageService;
import com.gameof3.gameof3artifact.chat.ChatNotification;
import com.gameof3.gameof3artifact.chat.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ClientMessageService {

    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    public void sendGameStartedMessage(Message chatMessage){
        Message gameOverMessage = new Message();
        gameOverMessage.setContent("Game has started by "+chatMessage.getSenderId());
        gameOverMessage.setSenderId(chatMessage.getSenderId());
        gameOverMessage.setRecipientId(chatMessage.getRecipientId());
        gameOverMessage.setTimestamp(new Date());
        gameOverMessage.setChatId(chatMessage.getChatId());
        Message savedMsg = chatMessageService.saveNew(gameOverMessage);
        sendMessageToClient(chatMessage, savedMsg);
    }

    public String sendRandomNumberMessage(Message chatMessage){
        Random rand = new Random();
        // Generate random integers in range 0 to 999
        int randomNumber = rand.nextInt(1000);
        Message randomNumberMsg = new Message();
        String randomNumContent = String.valueOf(randomNumber);
        randomNumberMsg.setContent(randomNumContent);
        randomNumberMsg.setSenderId(chatMessage.getSenderId());
        randomNumberMsg.setRecipientId(chatMessage.getRecipientId());
        randomNumberMsg.setTimestamp(new Date());
        randomNumberMsg.setChatId(chatMessage.getChatId());
        Message savedMsg = chatMessageService.saveNew(randomNumberMsg);
        sendMessageToClient(chatMessage, savedMsg);
        return randomNumContent;
    }

    public void sendInvalidNumberMessage(Message chatMessage){
        chatMessageService.saveNew(chatMessage);
        Message newMessage = new Message();
        newMessage.setContent("Please send -1 to start the game");
        newMessage.setSenderId(chatMessage.getRecipientId());// on behalf of recipient
        newMessage.setRecipientId(chatMessage.getSenderId());
        newMessage.setChatId(chatMessage.getChatId());
        newMessage.setTimestamp(new Date());
        Message savedMsg = chatMessageService.saveNew(newMessage);//"Please send -1 to start the game"
        sendMessageToClient(chatMessage, savedMsg);
    }

    public void sendInvalidSenderMessage(Message chatMessage){
        //chatMessageService.saveNew(chatMessage);
        Message newMessage = new Message();
        newMessage.setContent("Wait for my turn");
        newMessage.setSenderId(chatMessage.getRecipientId());// on behalf of recipient
        newMessage.setRecipientId(chatMessage.getSenderId());
        newMessage.setTimestamp(new Date());
        newMessage.setChatId(chatMessage.getChatId());
        //Message savedMsg = chatMessageService.saveNew(newMessage);//"Wait for my turn"
        sendMessageToClient(chatMessage, newMessage);
    }

    public void sendLastMessage(Message chatMessage){
        Message savedMsg = chatMessageService.saveNew(chatMessage);
        sendMessageToClient(chatMessage, savedMsg);
    }

    public void sendGameResultMessage(Message chatMessage){
        Message message = new Message();
        message.setContent("Game won by "+chatMessage.getSenderId());
        message.setSenderId(chatMessage.getSenderId());
        message.setRecipientId(chatMessage.getRecipientId());
        message.setTimestamp(new Date());
        Message gameOverMessage = chatMessageService.saveNew(message);//"Game over"
        sendMessageToClient(chatMessage, gameOverMessage);
    }

    public void sendRegularMessage(Message chatMessage){
        // set valid sender in Redis
        Message savedMsg = chatMessageService.saveNew(chatMessage);
        sendMessageToClient(chatMessage, savedMsg);
    }

    public void sendRegularInvalidMessage(Message chatMessage){
        Message newMessage = new Message();
        newMessage.setContent("Please send a valid number to continue the game");
        newMessage.setSenderId(chatMessage.getRecipientId());// on behalf of recipient
        newMessage.setRecipientId(chatMessage.getSenderId());
        newMessage.setTimestamp(new Date());
        // Message savedMsg = chatMessageService.saveNew(newMessage);//"Please send valid number to continue the game"
        sendMessageToClient(chatMessage, newMessage);
    }

    public void sendAutomaticMessage(Message chatMessage) {
        Message newMessage = new Message();
        newMessage.setContent("Automated message from "+chatMessage.getRecipientId());
        newMessage.setSenderId(chatMessage.getRecipientId());// on behalf of recipient
        newMessage.setRecipientId(chatMessage.getSenderId());
        newMessage.setTimestamp(new Date());
        Message savedMsg = chatMessageService.saveNew(newMessage);//"Please send valid number to continue the game"
        sendMessageToClient(chatMessage, newMessage);
    }

    public void sendAutomaticNumberMessage(Message chatMessage) {
        Message newMessage = new Message();
        int number = chatMessageService.processMessage(chatMessage);
        newMessage.setContent(String.valueOf(getNextMoveNum(number)));
        newMessage.setSenderId(chatMessage.getRecipientId());// on behalf of recipient
        newMessage.setRecipientId(chatMessage.getSenderId());
        newMessage.setTimestamp(new Date());
        Message savedMsg = chatMessageService.saveNew(newMessage);//"auto num"
        sendMessageToClient(chatMessage, savedMsg);
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

    private int sendMessageToClient(Message chatMessage,  Message savedMsg){
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