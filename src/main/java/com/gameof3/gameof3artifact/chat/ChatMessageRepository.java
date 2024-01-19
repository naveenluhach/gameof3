package com.gameof3.gameof3artifact.chat;

import com.gameof3.gameof3artifact.Game.Game;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<Message, String> {
    List<Message> findByChatId(String chatId);

    /*
     Find game id by status and chat id
     */
    /*
    @Query("{$and :[{status: ?0},{chatId: ?1}]}")
    Game findGameByStatusAnfChatId(String status, String chatId);

    */
    @Query("{$chatId: ?0}, {$limit : 1}, sort={timestamp:-1}")
    public Message findLatestMessageByChatId(String chatId, Sort sort);
}
