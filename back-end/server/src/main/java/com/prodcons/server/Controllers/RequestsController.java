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

    //for example: "/+shm/(M,1)/(Q,2)" adding an arrow from (M1) to (Q2)
    @PostMapping("/+shm/({fromType},{fromNumber})/({toType},{toNumber})")
    public boolean addArrow(
        @PathVariable("fromType") char fType,
        @PathVariable("fromNumber") int fNumber,
        @PathVariable("toType") char tType,
        @PathVariable("toNumber") int tNumber)
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
}
