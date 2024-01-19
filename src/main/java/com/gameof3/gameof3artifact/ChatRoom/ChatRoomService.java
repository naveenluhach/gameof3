package com.gameof3.gameof3artifact.ChatRoom;

import com.gameof3.gameof3artifact.chat.ChatMessageRepository;
import com.gameof3.gameof3artifact.chat.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    public Optional<String> getChatRoomId(
            String senderId,
            String recipientId,
            boolean createNewRoomIfNotExists
    ) {
        return chatRoomRepository
                .findBySenderIdAndRecipientId(senderId, recipientId)
                .map(ChatRoom::getChatId)
                .or(() -> {
                    if(createNewRoomIfNotExists) {// false
                        var chatId = createChatId(senderId, recipientId);
                        return Optional.of(chatId);
                    }

                    return  Optional.empty();
                });
    }

    // find if chatroom is present, if yes return it
    //else if room is empty and do not create, return
    public String checkIfChatRoomIdExists(String senderId, String recipientId) {
        ChatRoom chatRoom = chatRoomRepository.findBySenderIdAndRecipientIdcustom(senderId, recipientId);
        if(chatRoom==null){
           return "-1";
        }
        return chatRoom.getChatId();
    }

    // will only be called after checkIfChatRoomIdExists
    public ChatRoom findChatRoomId(String senderId, String recipientId){
        return chatRoomRepository.findBySenderIdAndRecipientIdcustom(senderId, recipientId);
    }

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

    public List<Message> findMessages(String chatRoomId){
        return chatMessageRepository.findByChatId(chatRoomId);
    }


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