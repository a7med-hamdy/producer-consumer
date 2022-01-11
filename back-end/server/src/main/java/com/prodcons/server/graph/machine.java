package com.prodcons.server.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.prodcons.server.websocket.WSService;

import org.json.JSONObject;

public class machine implements vertex, Runnable, observer {
    public String name;
    // list of the queues before the machine
    private List<waitingList> queues = Collections.synchronizedList(new ArrayList<waitingList>());
    // the queue after the machine
    private waitingList after;
    private Thread t;
    private int time = 0;
    public machine(String name, int time)
    {
        this.name = name;
        this.time = time;
        this.t = new Thread(this, this.name);
    }
    public machine(){}
    /**
     * the process method of the machine
     * @param product the product to be processed
     */
    private void process(String product) {
        //notify the front end that the machine acquired the product
        JSONObject obj = new JSONObject();
        obj.putOpt("name", this.name);
		obj.putOpt("change", product); 
        WSService.notifyFrontend(obj.toString());
        try{
            Thread.sleep(this.time);
        }catch(Exception e){System.out.println(e);}
        System.out.println(this.name + " processed " + product);
        //notify the front end that the machine finished processing
        JSONObject obj2 = new JSONObject();
        obj2.putOpt("name", this.name);
        obj2.putOpt("change", "flash"); 
        WSService.notifyFrontend(obj2.toString());
    }
    /**
     * a method to start the thread of the machine
     */
    public void startMachine()
    {
        this.t.start();
    }
    /**
     * set the queue after the machine
     * @param w the queue
     * @return 0 indicates success -1 indicates failure
     */
    public int setAfter(waitingList w)
    {
        if(this.after == null && !w.name.equalsIgnoreCase("Q0"))
        {
            this.after = w;
            return 0;
        }
        return -1;
    }
    /**
     * get the queue after the machine
     * @return the queue after the machine
     */
    public waitingList getAfter()
    {
        return this.after;
    }
    /**
     * add a queue before the machine
     * @param w the queue to be added
     */
    public void addQueue(waitingList w)
    {
        this.queues.add(w);
    }
    /**
     * uodate method to receive the notification from the queue and wake the thread
     */
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
    /**
     * run method of the thread
     */
    @Override
    public void run() {
        boolean wait = true;
        String product = "";
        while(true)
        {
            wait = true;
            synchronized(this.queues){
                //iterate the queues before and search for a product
                Iterator<waitingList> i  = this.queues.iterator();
                while(i.hasNext())
                {
                    waitingList q = i.next();
                    synchronized(q){
                        product = q.getProduct(); 
                        if(product != null)
                        {
                            // if a product found break
                            wait = false;
                            break;
                        } 
                    }
                }
            }
            //if no product found sleep
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
            // process the product and give to the queue after
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
    /**
     * get the name of the machine
     */
    public String getName() {
        return this.name;
    }
}
