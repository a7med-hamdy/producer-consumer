package com.prodcons.server.graph;

import java.util.LinkedList;
import java.util.Queue;


public class waitingList implements vertex,Runnable{
    machine machine;
    Thread t=new Thread(this);
    String label;
    Queue<String> list= new LinkedList<String>();
    Boolean flage=true;
    public waitingList(machine machine, Thread thread,String label) {
        this.label=label;
        this.machine=machine;
        this.t.start();
    }
    
    public void update(Boolean flage){
        this.flage=flage;
    }

    @Override
   public void run() {
    while(flage && !(this.list.isEmpty())){
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
