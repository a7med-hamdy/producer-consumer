package com.prodcons.server.graph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class waitingList implements vertex, Runnable{
    public String name;
    private Queue<String> list= new LinkedList<String>();
    private List<observer> subscribers = Collections.synchronizedList(new ArrayList<observer>());
    private Thread t; 
    public waitingList(String name) {
        this.name = name;
        this.t = new Thread(this, this.name);
        // this.t.start();
    }
    public waitingList(){}

    public void Notify()
    {
        Iterator<observer> i = this.subscribers.iterator();
        while(i.hasNext())
        {
            i.next().update();
        }
    }
    public void subscribe(machine m)
    {
        this.subscribers.add(m);
    }
    public void add(String str){
        if(list.size() == 0)
        {
            this.Notify();
        }
        list.add(str);
    }

    public String getProduct()
    {
        if(this.list.size() == 0)
        {
            return null;
        }
        System.out.println(Thread.currentThread().getName()+" is getting the product");
        return this.list.poll();
    }
    @Override
    public void run() {
        File f = new File("src\\"+this.name+".txt");
        try {
            f.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        FileWriter writer;
        try {
            writer = new FileWriter(f, true);
            BufferedWriter br = new BufferedWriter(writer);
            while(true)
            {
                br.write(this.list.size()+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
