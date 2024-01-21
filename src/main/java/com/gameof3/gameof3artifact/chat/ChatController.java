package com.gameof3.gameof3artifact.chat;

import com.gameof3.gameof3artifact.Orchestrate.OrchestrateService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
/**
 * ChatController to handle messages between 2 players
 */
public class ChatController {

    /**
     * PlayerMessageService dependency for handling player message related operations
     */
    private final PlayerMessageService playerMessageService;

    /**
     * OrchestrateService dependency for orchestrating and coordinating game-related operations
     */
    private final OrchestrateService orchestrateService;

    Logger logger = LoggerFactory.getLogger(ChatController.class);

    /**
     * Method to process message passing and checks and balance on the overall flow, think of this like orchestrator
     * @param playerMessage
     */
    @MessageMapping("/chat")
    public void processMessage(@Payload PlayerMessage playerMessage) {
        logger.info("Received a payload : "+playerMessage);
        orchestrateService.processPlayerMessage(playerMessage);
    }

    /**
     * Method to find previous chat messages when a player logs into the application, this method handles the offline user feature
     * @param senderId
     * @param recipientId
     * @return
     */
    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<PlayerMessage>> findChatMessages(@PathVariable String senderId,
                                                                @PathVariable String recipientId) {
        logger.info("Received a request to fetch all messages between with senderId: "+ senderId +" recepientId: "+recipientId);
        return ResponseEntity
                .ok(playerMessageService.findChatMessages(senderId, recipientId));
    }
}
