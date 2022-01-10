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
            this.graph.addQueue();
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
            this.graph.addMachine();
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
        String str = "";
        try{
            str = graph.addEdge(from, to);
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        if(str.equalsIgnoreCase("success"))
        {
            return true;
        }
        return false;
    }

/**************************************************
 * Getting & Deleting requests                    *
 **************************************************/
    @GetMapping("/getGraph/{id}")
    public String getGraph(@PathVariable int id){
        try{
            return this.graph.getGraphJson();
        }
        catch(Exception e){
            return "Graph";
        }
    }
 
    @DeleteMapping("/clear")
    public boolean clearGraph(){
        try{
            this.graph=new Graph();
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
    public void play(){
        this.graph.startSimulation();
        this.origin.setState(graph);
        this.cTaker.addMemento(origin.getMemento());
    }

    @PostMapping("/validate")
    public boolean validate(){
        return this.graph.valdiateSimulation();
    }
    
    @PostMapping("/replay")
    public boolean replay(){
        try{
            this.graph=cTaker.getMemento().getState();
            this.graph.replay();
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
