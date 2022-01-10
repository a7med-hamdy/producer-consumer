package com.prodcons.server.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.prodcons.server.websocket.WSService;

import org.json.JSONObject;

public class machine implements vertex, Runnable, observer {
    public String name;
    private List<waitingList> queues = Collections.synchronizedList(new ArrayList<waitingList>());
    private waitingList after;
    private Thread t;
    private int time = 0;
    public machine(String name, int time)
    {
        this.name = name;
        this.time = time;
        this.t = new Thread(this, this.name);
        this.t.start();
    }
    public machine(){}

    private void process(String product) {
        JSONObject obj = new JSONObject();
        obj.putOpt("name", this.name);
		obj.putOpt("change", product); 
        WSService.notifyFrontend(obj.toString());
        try{
            Thread.sleep(this.time);
        }catch(Exception e){System.out.println(e);}
        System.out.println(this.name + " processed " + product);
        JSONObject obj2 = new JSONObject();
        obj2.putOpt("name", this.name);
        obj2.putOpt("change", "flash"); 
        WSService.notifyFrontend(obj2.toString());
    }

    public int setAfter(waitingList w)
    {
        if(this.after == null && !w.name.equalsIgnoreCase("Q0"))
        {
            this.after = w;
            return 0;
        }
        return -1;
    }

    public void addQueue(waitingList w)
    {
        this.queues.add(w);
    }
    public void update()
    {
        if(this.t.getState().toString().equalsIgnoreCase("waiting"))
        {
            System.out.println(this.t.getName()+ " is notified");
            synchronized(this.queues)
            {
                this.queues.notifyAll();
            }
        }
    }
    @Override
    public void run() {
        boolean wait = true;
        String product = "";
        while(true)
        {
            wait = true;
            synchronized(this.queues){
                Iterator<waitingList> i  = this.queues.iterator();
                while(i.hasNext())
                {
                    waitingList q = i.next();
                    synchronized(q){
                        product = q.getProduct(); 
                        if(product != null)
                        {
                            wait = false;
                            break;
                        } 
                    }
                }
            }
            if(wait)
            {
                System.out.println(Thread.currentThread().getName()+" is going to sleep");
                try {
                    synchronized(this.queues){this.queues.wait();}
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" woke up");
                continue;
            }
            this.process(product);
            if(this.after != null)
            {
                synchronized(this.after)
                {
                    this.after.add(product);
                } 
            }
        }
    }
    @Override
    public String getName() {
        return this.name;
    }
}
