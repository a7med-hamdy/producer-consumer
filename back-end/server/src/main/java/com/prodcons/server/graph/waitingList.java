package com.prodcons.server.graph;

import java.util.LinkedList;
import java.util.Queue;


public class waitingList implements vertex{
    public String name;
    private Queue<String> list= new LinkedList<String>();
    public waitingList(String name) {
        this.name = name;
    }


    public void add(String str){
        if(list.size() == 0)
        {
            notifyAll();
        }
        list.add(str);
    }

    public String getProduct()
    {
        if(this.list.size() == 0)
        {
            System.out.println(Thread.currentThread().getName()+" is going to sleep");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName()+" is getting the product");
        return this.list.poll();
    }
    public int getSize()
    {
        return this.list.size();
    }
}
