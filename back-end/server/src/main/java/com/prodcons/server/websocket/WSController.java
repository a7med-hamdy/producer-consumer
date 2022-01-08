package com.prodcons.server.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.util.HtmlUtils;

@Controller
@RestController
public class WSController {

    @Autowired
    private WSService service;
    
    @MessageMapping("/hello") //accessed from 'http://localhost:8080/app/hello'
    @SendTo("/topic/board") //The frontend topic to send data back
    public boolean updateWihChange(String board) throws Exception {
        System.out.println("Great Jop, " + board);
        // Thread.sleep(2000);
        // return "The change: " + HtmlUtils.htmlEscape(board + "!!");
        try{
            //...do the creation here
        }
        catch(Exception e){
            return false;
        }
        return true;
    }

    //Just for testing: (sending from backend to frontend)
    @PostMapping("/send")
    public void sendToFront(@RequestParam("msg") final String message,
                            @RequestParam("num") int number){

        System.out.println("to be sent: " + message + number);
        service.notifyFrontend(message + number);
    }
}
