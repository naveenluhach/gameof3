package com.gameof3.gameof3artifact.ChatRoom;

import com.gameof3.gameof3artifact.chat.ChatMessageRepository;
import com.gameof3.gameof3artifact.chat.PlayerMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
/**
 * Service class for managing chat rooms and messages.
 */
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    /**
     * Checks if a chat room exists between the given sender and recipient.
     * Returns the chat ID if the chat room exists, otherwise returns "-1".
     * @param senderId The ID of the sender
     * @param recipientId The ID of the recipient
     * @return The chat ID if the chat room exists, otherwise "-1"
     */
    public String checkIfChatRoomIdExists(String senderId, String recipientId) {
        ChatRoom chatRoom = chatRoomRepository.findBySenderIdAndRecipientIdcustom(senderId, recipientId);
        if(chatRoom==null){
           return "-1";
        }
        return chatRoom.getChatId();
    }

    /**
     * Finds the chat room ID between the given sender and recipient.
     * @param senderId The ID of the sender
     * @param recipientId The ID of the recipient
     * @return The chat room ID if found, otherwise null
     */
    public ChatRoom findChatRoomId(String senderId, String recipientId){
        return chatRoomRepository.findBySenderIdAndRecipientIdcustom(senderId, recipientId);
    }

    /**
     * Gets or creates a chat ID between the sender and recipient.
     * If the chat room exists, returns its ID. Otherwise, creates a new chat room and returns its ID.
     * @param senderId The ID of the sender
     * @param recipientId The ID of the recipient
     * @return The chat ID
     */
    public String getOrCreateChatId(String senderId, String recepientId){
        var chatId = "";
        String chatRoomExists = checkIfChatRoomIdExists(senderId, recepientId);
        if(!chatRoomExists.equals("-1")){
            //find chatid room
            ChatRoom chatRoom = findChatRoomId(senderId, recepientId);
            chatId = chatRoom.getChatId();
        }else{
            chatId = createChatId(senderId, recepientId);
        }
        return chatId;
    }

    /**
     * Finds all chat messages of a chat room based on the provided chatRoomId.
     * @param chatRoomId The ID of the chat room
     * @return List of chat messages
     */
    public List<PlayerMessage> findMessages(String chatRoomId){
        return chatMessageRepository.findByChatId(chatRoomId);
    }

    /**
     * Creates a chat ID between the sender and recipient.
     * Creates two chat room entities for sender-to-recipient and recipient-to-sender communication.
     * @param senderId The ID of the sender
     * @param recipientId The ID of the recipient
     * @return The created chat ID
     */
    public String createChatId(String senderId, String recipientId) {
        var chatId = String.format("%s_%s", senderId, recipientId);

        ChatRoom senderRecipient = ChatRoom
                .builder()
                .chatId(chatId)
                .senderId(senderId)
                .recipientId(recipientId)
                .build();

        ChatRoom recipientSender = ChatRoom
                .builder()
                .chatId(chatId)
                .senderId(recipientId)
                .recipientId(senderId)
                .build();

        chatRoomRepository.save(senderRecipient);
        chatRoomRepository.save(recipientSender);

        return chatId;
    }
}