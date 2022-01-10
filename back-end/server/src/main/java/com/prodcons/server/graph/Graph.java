package com.prodcons.server.graph;

import java.util.ArrayList;
import java.util.Set;

import com.prodcons.server.websocket.WSService;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.json.JSONArray;
import org.json.JSONObject;
import org.webjars.NotFoundException;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultEdge;

public class Graph {
    private DirectedAcyclicGraph<vertex, DefaultEdge> g = new DirectedAcyclicGraph<>(DefaultEdge.class);
    private ArrayList<machine> machines = new ArrayList<>();
    private waitingList rootQueue;
    private ArrayList<waitingList> queues = new ArrayList<>();
    private ArrayList<Integer> times = new ArrayList<>();
    private ArrayList<Integer> indexes = new ArrayList<>();
    private String shapes = "";
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
        cyan,
    }
    //for machine 
    public void addMachine() {
        int min=2000,max=5000;
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
    public boolean valdiateSimulation()
    {
        for (machine m : machines)
        {
            if(m.getAfter() == null){return false;}
        }
        return true;
    }
    public void startSimulation()
    {
        // System.out.println("starting input");
        // synchronized(this.rootQueue){
        // try {
        //     Thread.sleep(20);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        // this.rootQueue.add("blue");
        // this.rootQueue.add("red");
        // this.rootQueue.add("cyan");
        // this.rootQueue.add("black");
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
        // }
       
        for(waitingList w : queues)
        {
            JSONObject obj = new JSONObject();
            if(w.getSubscribersNumber() == 0)
            {
                System.out.println(w.name);
                obj.putOpt("name", w.name);
		        obj.putOpt("change", "empty"); 
                WSService.notifyFrontend(obj.toString());
            }
            
        }
        for(machine m : machines)
        {
            m.startMachine();
        }
        for(int i = 0; i < 10; i++)
        {
            int min=2000,max=5000;
            int time=(int)Math.floor(Math.random()*(max-min+1)+min);
            this.times.add(time);
            min = 0; max = 6;
            int index = (int)Math.floor(Math.random()*(max-min+1)+min);
            this.indexes.add(index);
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized(this.rootQueue)
            {
                this.rootQueue.add(colors.values()[index].toString());
            }
        }
    }
    public String getGraphJson()
    {
        JSONArray arr = new JSONArray();
        Set<vertex> s = this.g.vertexSet();
        for(vertex v: s)
        {
            JSONObject obj = new JSONObject();
            obj.putOpt("name", v.getName());
            Set<DefaultEdge> descendants = this.g.outgoingEdgesOf(v);
            JSONArray to = new JSONArray();
            for(DefaultEdge descendant : descendants)
            {
                to.put(this.g.getEdgeTarget(descendant).getName());
            }
            obj.putOpt("to", to);
            arr.put(obj);
        }
        
        return arr.toString();
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
    public void replay(){
        System.out.println("////////////////////////start replay///////////////////////////////");
        for(waitingList w : queues)
        {
            JSONObject obj = new JSONObject();
            if(w.getSubscribersNumber() == 0)
            {
                System.out.println(w.name);
                obj.putOpt("name", w.name);
		        obj.putOpt("change", "empty"); 
                WSService.notifyFrontend(obj.toString());
            }
            
        }
        for(waitingList w : this.queues)
        {
            w.restartQueue();
        }
        for(int i = 0; i < 10; i++)
        {
            int time= this.times.get(i);
            int index = this.indexes.get(i);
            this.indexes.add(index);
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized(this.rootQueue)
            {
                this.rootQueue.add(colors.values()[index].toString());
            }
        }
   }
   public void setShapes(String str)
   {
       this.shapes = str;
   }
   public String getShapes()
   {
       return this.shapes;
   } 
}
