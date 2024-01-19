package com.gameof3.gameof3artifact.Game;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;


/*
Repository class to handle CRUD operations of Game Entity
 */
public interface GameRepository extends MongoRepository<Game, String> {

    List<Game> findByChatId(String chatId);

}
