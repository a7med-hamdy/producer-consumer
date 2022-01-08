package com.prodcons.server.graph;

import java.util.ArrayList;

public class machine implements vertex, Runnable {
    public String name;
    private ArrayList<waitingList> queues;
    private waitingList after;
    private Thread t;
    private int time = 0;
    public machine(ArrayList<waitingList> queues, String name, int time)
    {
        this.name = name;
        this.time = time;
        this.t = new Thread(this, this.name);
        this.t.start();
        this.queues = queues;
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

    @Override
    public void run() {
        while(true)
        {
            for (waitingList q : this.queues)
            {
                String product = "";
                synchronized(q){
                    product = q.getProduct();  
                }
                if(product == null)
                {
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
    }
}
