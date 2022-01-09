package com.prodcons.server.Controllers;

import com.prodcons.server.graph.*;


import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class RequestsController {
    Graph graph=new Graph();
    Originator origin= new Originator();
    careTaker cTaker= new careTaker();

/**************************************************
 * Craeation board requests                       *
 **************************************************/
    @PostMapping("/+Q")
    public boolean addQueue(){
        try{
            graph.addQueue();
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @PostMapping("/+M")
    public boolean addMachine(){
        try{
            graph.addMachine();
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        System.out.println("Machine");
        return true;
    }

    //for example: "/+shm/M1/Q2" adding an arrow from (M1) to (Q2)
    @PostMapping("/+shm/{from}/{to}")
    public boolean addArrow(
        @PathVariable("from") String from,
        @PathVariable("to") String to)
    {
        try{
            graph.addEdge(from, to);
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

/**************************************************
 * Getting & Deleting requests                    *
 **************************************************/
    @GetMapping("/getGraph/{id}")
    public String getGraph(@PathVariable int id){
        try{

        }
        catch(Exception e){
            return "Graph";
        }
        return null;
    }
 
    @DeleteMapping("/clear")
    public boolean clearGragh(){
        try{
            graph=new Graph();
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
           graph.startSimulation();
           origin.setState(graph);
           cTaker.addMemento(origin.getMemento());
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /* @PostMapping("/stop")
    public boolean stop(){
        try{
            //Stop it :)
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    } */

    @PostMapping("/replay")
    public boolean replay(){
        try{
            graph=cTaker.getMemento().getState();
            graph.replay();
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
