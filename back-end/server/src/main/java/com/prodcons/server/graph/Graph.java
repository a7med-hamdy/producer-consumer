package com.prodcons.server.graph;

import java.util.ArrayList;


import org.jgrapht.graph.DirectedAcyclicGraph;
import org.webjars.NotFoundException;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultEdge;

public class Graph {
    private DirectedAcyclicGraph<vertex, DefaultEdge> g = new DirectedAcyclicGraph<>(DefaultEdge.class);
    private ArrayList<machine> machines = new ArrayList<>();
    private waitingList rootQueue;
    private ArrayList<waitingList> queues = new ArrayList<>();
    public Graph ()
    {
        rootQueue = new waitingList("Q" + this.queues.size());
        this.queues.add(rootQueue);
        this.g.addVertex(rootQueue);
    }
    enum colors{
        red,
        blue,
        green,
        yellow,
        black, 
        violet,
    }
    //for machine 
    public void addMachine() {
        int min=100,max=10000;
        int time=(int)Math.floor(Math.random()*(max-min+1)+min);
        machine m = new machine("M" + this.machines.size(),time);
        this.machines.add(m);
        this.g.addVertex(m);
    }
    // for queue
    public void addQueue()
    {
        waitingList q = new waitingList("Q" + this.queues.size());
        this.queues.add(q);
        this.g.addVertex(q);
    }
    //add edges (arrows)
    public String addEdge(String src, String dst)
    {
        Pair<machine,String> found = new Pair<machine,String>(new machine(), "");
        Pair<waitingList,String> found2 = new Pair<waitingList,String>(new waitingList(), "");
        try{
            found = this.findMachine(src, dst);
        }
        catch(NotFoundException e)
        {
            return "incompatible connection";
        }
        try{
            found2 = this.findQueue(src, dst);
        }
        catch(NotFoundException e)
        {
            return "incompatible connection";
        }
        if(found.getSecond().equalsIgnoreCase("src"))
        {
            try{
                g.addEdge(found.getFirst(), found2.getFirst());
                if(found.getFirst().setAfter(found2.getFirst()) == -1)
                {
                    return "machines have only one queue after it";
                }
            }
            catch(IllegalArgumentException  e)
            {
                System.out.println("error cycle");
                return "error cycle";
            }
        }
        else
        {
            try{
                g.addEdge(found2.getFirst(), found.getFirst());
                found.getFirst().addQueue(found2.getFirst());
                found2.getFirst().subscribe(found.getFirst());
            }
            catch(IllegalArgumentException  e)
            {
                System.out.println("error cycle");
                return "error cycle";
            }
        }
        return "success";
    }
    public void startSimulation()
    {
        System.out.println("starting input");
        // try {
        //     Thread.sleep(20);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        // this.rootQueue.add("blue");
        // this.rootQueue.add("red");
        // try {
        //     Thread.sleep(2000);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        // this.rootQueue.add("violet");
        // try {
        //     Thread.sleep(1500);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        // this.rootQueue.add("green");
        // this.rootQueue.add("yellow");
        for(int i = 0; i < 10; i++)
        {
            
        }
    }
    private Pair<waitingList,String> findQueue(String src, String dst) throws NotFoundException
    {
        for(waitingList w : this.queues)
        {
            if(w.name.equalsIgnoreCase(src))
            {
                return new Pair<>(w, "src");
            }
            if(w.name.equalsIgnoreCase(dst))
            {
                return new Pair<>(w, "dst");
            }
        }
        System.out.println("unexpected Error: machine not found");
        throw new NotFoundException("unexpected Error: queue not found");
    }

    private Pair<machine,String> findMachine(String src, String dst) throws NotFoundException
    {
        for(machine m : this.machines)
        {
            if(m.name.equalsIgnoreCase(src))
            {
                return new Pair<>(m,"src");
            }
            if(m.name.equalsIgnoreCase(dst))
            {
                return new Pair<>(m,"dst");
            }
        }
        System.out.println("unexpected Error: machine not found");
        throw new NotFoundException("unexpected Error: machine not found");
    }
}
