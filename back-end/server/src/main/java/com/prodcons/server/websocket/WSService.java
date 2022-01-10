package com.prodcons.server.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WSService {

    private static  SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WSService(SimpMessagingTemplate messagingTemplate){
        WSService.messagingTemplate = messagingTemplate;
    }

    
    /**
     * Sends the update to the Frontend
     * @param message
     */
    public static synchronized void notifyFrontend(final String message){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //send the message to all subscribers
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        messagingTemplate.convertAndSend("/topic/board", message);
    }
}
