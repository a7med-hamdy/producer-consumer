package com.prodcons.server.graph;

import java.util.LinkedList;
import java.util.Queue;


public class waitingList implements vertex,Runnable{
    machine machine;
    Thread t;
    String label;
    Queue<String> list= new LinkedList<String>();

    public waitingList(machine machine, Thread thread,String label) {
        this.t=thread;
        this.label=label;
        this.machine=machine;
        this.t.start();
    }
    
    @Override
    public void run() {
        while(!(this.list.isEmpty())){
        this.machine.process(this.label,this.list.poll());
        }
    }

    public void add(){
        list.add("new product");
    }
    public void show(){
        System.out.println(this.list);
    }

}
