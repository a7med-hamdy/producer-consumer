package com.prodcons.server.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WSService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WSService(SimpMessagingTemplate messagingTemplate){
        this.messagingTemplate = messagingTemplate;
    }

    
    /**
     * Sends the update to the Frontend
     * @param message
     */
    public void notifyFrontend(final String message){

        //send the message to all subscribers
        messagingTemplate.convertAndSend("/topic/board", message);
    }
}
