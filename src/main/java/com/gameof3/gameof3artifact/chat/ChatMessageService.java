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

    public Message save(Message chatMessage) {
        var chatId = chatRoomService
                .getChatRoomId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true)
                .orElseThrow(); // You can create your own dedicated exception
        chatMessage.setChatId(chatId);
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

    public Message saveNew(Message chatMessage){
        //check if chat id exists
        // if not create one
        // save the message
        String chatId = "";
        String chatRoomExists = chatRoomService.checkIfChatRoomIdExists(chatMessage.getSenderId(), chatMessage.getRecipientId());
        if(!chatRoomExists.equals("-1")){
            //find chatid room
            ChatRoom chatRoom = chatRoomService.findChatRoomId(chatMessage.getSenderId(), chatMessage.getRecipientId());
            chatId = chatRoom.getChatId();
        }else{
            chatId = chatRoomService.createChatId(chatMessage.getSenderId(), chatMessage.getRecipientId());
        }
        chatMessage.setChatId(chatId);
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }


    public List<Message> findChatMessages(String senderId, String recipientId) {
        //var chatId = chatRoomService.getChatRoomId(senderId, recipientId, false);
        String chatRoomId= chatRoomService.checkIfChatRoomIdExists(senderId, recipientId);
        if(!chatRoomId.equals("-1")){
             return chatRoomService.findMessages(chatRoomId);
        }else{
            return new ArrayList<>();
        }
        //return chatId.map(chatMessageRepository::findByChatId).orElse(new ArrayList<>());
    }

    // method to check if game start or end has reached
    public int processMessage(Message chatMessage){
        String content  = chatMessage.getContent();
        int number = Integer.parseInt(content);
        return number;
    }

    public boolean isValidSender(Message chatMessage){
        Message lastMessage  = getlastMessage(chatMessage);
        String lastSenderId = lastMessage.getSenderId();
        if(chatMessage.getSenderId().equals(lastSenderId)){
            return false;
        }else{
            return true;
        }
    }

    public int getLastNumber(Message message){
        Message lastMessage  = getlastMessage(message);
        String lastNumber = lastMessage.getContent();
        return Integer.valueOf(lastNumber);
    }

    public Message getlastMessage(Message chatMessage){
       List<Message> messages = chatMessageRepository.findByChatId(chatMessage.getChatId());
       // sort the list with highest timestamp
        Collections.sort(messages, (m1, m2) -> m2.getTimestamp().compareTo(m1.getTimestamp()));
        Message latestMessage = messages.isEmpty() ? null : messages.get(0);
        return latestMessage;
    }
}