package com.gameof3.gameof3artifact.chat;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<Message, String> {
    List<Message> findByChatId(String chatId);
}
