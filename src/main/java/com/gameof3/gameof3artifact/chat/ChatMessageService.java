package com.gameof3.gameof3artifact.chat;

import com.gameof3.gameof3artifact.ChatRoom.ChatRoom;
import com.gameof3.gameof3artifact.ChatRoom.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;

    /*
    Save the chatMessage to DB
     */
    public PlayerMessage saveNew(PlayerMessage chatPlayerMessage){
        String chatId = "";
        String chatRoomExists = chatRoomService.checkIfChatRoomIdExists(chatPlayerMessage.getSenderId(), chatPlayerMessage.getRecipientId());
        if(!chatRoomExists.equals("-1")){
            ChatRoom chatRoom = chatRoomService.findChatRoomId(chatPlayerMessage.getSenderId(), chatPlayerMessage.getRecipientId());
            chatId = chatRoom.getChatId();
        }else{
            chatId = chatRoomService.createChatId(chatPlayerMessage.getSenderId(), chatPlayerMessage.getRecipientId());
        }
        chatPlayerMessage.setChatId(chatId);
        chatMessageRepository.save(chatPlayerMessage);
        return chatPlayerMessage;
    }


    /*
    finds the chat messages for a sender
     */
    public List<PlayerMessage> findChatMessages(String senderId, String recipientId) {
        String chatRoomId= chatRoomService.checkIfChatRoomIdExists(senderId, recipientId);
        if(!chatRoomId.equals("-1")){
             return chatRoomService.findMessages(chatRoomId);
        }else{
            return new ArrayList<>();
        }
    }

    /*
     method to find the integer message from chat message
     */
    public int processMessage(PlayerMessage chatPlayerMessage){
        String content  = chatPlayerMessage.getContent();
        int number = Integer.parseInt(content);
        return number;
    }

    /*
    method to check if sender is valid (should not be last sender) or not
     */
    public boolean isValidSender(PlayerMessage chatPlayerMessage){
        PlayerMessage lastPlayerMessage = getlastMessage(chatPlayerMessage);
        String lastSenderId = lastPlayerMessage.getSenderId();
        if(chatPlayerMessage.getSenderId().equals(lastSenderId)){
            return false;
        }else{
            return true;
        }
    }

    /*
    method to find the last number sent between 2 players
     */
    public int getLastNumber(PlayerMessage playerMessage){
        PlayerMessage lastPlayerMessage = getlastMessage(playerMessage);
        String lastNumber = lastPlayerMessage.getContent();
        return Integer.valueOf(lastNumber);
    }

    /*
    method to find the last message sent between 2 players
     */
    public PlayerMessage getlastMessage(PlayerMessage chatPlayerMessage){
       List<PlayerMessage> playerMessages = chatMessageRepository.findByChatId(chatPlayerMessage.getChatId());
       // sort the list with highest timestamp
        Collections.sort(playerMessages, (m1, m2) -> m2.getTimestamp().compareTo(m1.getTimestamp()));
        PlayerMessage latestPlayerMessage = playerMessages.isEmpty() ? null : playerMessages.get(0);
        return latestPlayerMessage;
    }
}
