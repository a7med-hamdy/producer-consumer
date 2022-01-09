package com.prodcons.server.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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
        try{
            Thread.sleep(time);
        }catch(Exception e){System.out.println(e);}
        System.out.println(this.name + " processed " + product);
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
        System.out.println(this.t.getName()+ " is notified");
        synchronized(this.queues)
        {
            this.queues.notifyAll();
        }
    }
    @Override
    public void run() {
        boolean wait = true;
        while(true)
        {
            synchronized(this.queues){
                Iterator<waitingList> i  = this.queues.iterator();
                while(i.hasNext())
                {
                    wait = true;
                    waitingList q = i.next();
                    String product = "";
                    product = q.getProduct(); 
                    if(product == null)
                    {
                        continue;
                    }
                    wait = false;
                    this.process(product);
                    if(this.after != null)
                    {
                        synchronized(this.after)
                        {
                            this.after.add(product);
                        }  
                    }
                }
                if(wait)
                {
                    System.out.println(Thread.currentThread().getName()+" is going to sleep");
                    try {
                        this.queues.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
