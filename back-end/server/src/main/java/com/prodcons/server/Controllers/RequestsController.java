package com.prodcons.server.Controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class RequestsController {
    
/**************************************************
 * Craeation board requests                       *
 **************************************************/
    
    @PostMapping("/+Q")
    public boolean addQueue()
    {
        try{
            //add the Queue
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @PostMapping("/+M")
    public boolean addMachine()
    {
        try{
            //add the Machine
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //for example: "/+shm/M1/Q2" adding an arrow from (M1) to (Q2)
    @PostMapping("/+shm/{from}/{to}")
    public boolean addArrow(
        @PathVariable("from") String from,
        @PathVariable("to") String to)
    {
        try{
            //add the Arrow
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

/**************************************************
 * Simulation requests                            *
 **************************************************/
    @PostMapping("/play")
    public boolean play(){
        try{
            //Let's play
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @PostMapping("/stop")
    public boolean stop(){
        try{
            //Stop it :)
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @PostMapping("/replay/{simulationID}")
    public boolean replay(@PathVariable int simulationID){
        try{
            //Replay this simulation
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
