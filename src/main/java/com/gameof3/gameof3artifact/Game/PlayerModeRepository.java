package com.gameof3.gameof3artifact.Game;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerModeRepository extends MongoRepository<PlayerMode, String> {

    PlayerMode findByPlayer(String user);
}
