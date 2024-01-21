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
public class PlayerMessageService {

    /**
     * ChatMessageRepository dependency for handling chat message-related operations.
     */
    private final ChatMessageRepository chatMessageRepository;

    /**
     * ChatRoomService dependency for managing chat rooms and associated operations.
     */
    private final ChatRoomService chatRoomService;

    /**
     * Save the chatMessage to DB
     * @param playerMessage
     * @return
     */
    public PlayerMessage save(PlayerMessage playerMessage){
        String chatId = "";
        String chatRoomExists = chatRoomService.checkIfChatRoomIdExists(playerMessage.getSenderId(), playerMessage.getRecipientId());
        if(!chatRoomExists.equals("-1")){
            ChatRoom chatRoom = chatRoomService.findChatRoomId(playerMessage.getSenderId(), playerMessage.getRecipientId());
            chatId = chatRoom.getChatId();
        }else{
            chatId = chatRoomService.createChatId(playerMessage.getSenderId(), playerMessage.getRecipientId());
        }
        playerMessage.setChatId(chatId);
        chatMessageRepository.save(playerMessage);
        return playerMessage;
    }

    /**
     * Method to find the chat messages for a sender
     * @param senderId
     * @param recipientId
     * @return
     */
    public List<PlayerMessage> findChatMessages(String senderId, String recipientId) {
        String chatRoomId= chatRoomService.checkIfChatRoomIdExists(senderId, recipientId);
        if(!chatRoomId.equals("-1")){
             return chatRoomService.findMessages(chatRoomId);
        }else{
            return new ArrayList<>();
        }
    }

    /**
     * Method to convert string message to int message
     * @param playerMessage
     * @return
     */
    public int processMessage(PlayerMessage playerMessage){
        String content  = playerMessage.getContent();
        int number = Integer.parseInt(content);
        return number;
    }

    /**
     * Method to check if sender is valid (should not be last sender) or not
     * @param playerMessage
     * @return
     */
    public boolean isValidSender(PlayerMessage playerMessage){
        PlayerMessage lastPlayerMessage = getlastMessage(playerMessage);
        String lastSenderId = lastPlayerMessage.getSenderId();
        if(playerMessage.getSenderId().equals(lastSenderId)){
            return false;
        }else{
            return true;
        }
    }

    /**
     * Method to find the last number sent between 2 players
     * @param playerMessage
     * @return int
     */
    public int getLastNumber(PlayerMessage playerMessage){
        PlayerMessage lastPlayerMessage = getlastMessage(playerMessage);
        String lastNumber = lastPlayerMessage.getContent();
        return Integer.valueOf(lastNumber);
    }


    /**
     * Method to find the last message sent between 2 players
     * @param playerMessage
     * @return PlayerMessage
     */
    private PlayerMessage getlastMessage(PlayerMessage playerMessage){
       List<PlayerMessage> playerMessages = chatMessageRepository.findByChatId(playerMessage.getChatId());
       // sort the list with highest timestamp
        Collections.sort(playerMessages, (m1, m2) -> m2.getTimestamp().compareTo(m1.getTimestamp()));
        PlayerMessage latestPlayerMessage = playerMessages.isEmpty() ? null : playerMessages.get(0);
        return latestPlayerMessage;
    }

    /**
     * Method to find valid response
     * @param playerMessage
     * @return int
     */
    public int getNextMoveNum(PlayerMessage playerMessage){
        int lastNumberbetweenUsers = getLastNumber(playerMessage);
        System.out.println("last number discussed between users: "+lastNumberbetweenUsers);
        int remainder = lastNumberbetweenUsers%3;
        int result = 0;
        if (remainder == 0) {
            result = lastNumberbetweenUsers/3;
        } else if (remainder == 1) {
            result = (lastNumberbetweenUsers - 1)/3;
        }else {
            result = (lastNumberbetweenUsers + 1) / 3;
        }
        System.out.println("Server num sent"+result);
        return result;
    }
}
