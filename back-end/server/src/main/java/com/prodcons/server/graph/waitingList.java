package com.prodcons.server.graph;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import com.prodcons.server.websocket.WSService;

import org.json.JSONObject;

//waiting list class acts as the queue 
public class waitingList implements vertex{
    public String name;
    private Queue<String> list= new LinkedList<String>();
    private ArrayList<observer> subscribers = new ArrayList<observer>();
    public waitingList(String name) {
        this.name = name;
    }
    public waitingList(){}
    /**
     * restart the queues for the start of the replay
     */
    public void restartQueue()
    {
        while(!this.list.isEmpty())
        {
            this.list.poll();
        }
    }
    /**
     * check the number of machines following a queue
     * it helps to identify the last queues in the simulation
     * by following the number of products in them the end of simulation can be determined 
     * @return
     */
    public int getSubscribersNumber()
    {
        return this.subscribers.size();
    }
    /**
     * a method to notify the subscribers of each queue
     */
    public void Notify()
    {
        Iterator<observer> i = this.subscribers.iterator();
        while(i.hasNext())
        {
            i.next().update();
        }
    }
    /**
     * a method used by the machines (observers) to subscribe and be notified by any change
     * @param m : the machine subscribing
     */
    public void subscribe(machine m)
    {
        this.subscribers.add(m);
    }
    /**
     * add a product to the string
     * @param str the product to be added
     */
    public void add(String str){
        this.Notify();
        list.add(str);
        //Notify the forntend 
        JSONObject obj = new JSONObject();
		obj.putOpt("name", this.name);
		obj.putOpt("change", this.list.size()); 
        WSService.notifyFrontend(obj.toString());
    }
    /**
     * a method to gets a product out of the queue
     * @return the product
     */
    public String getProduct()
    {
        if(this.list.size() == 0)// if the list is empty return null
        {
            return null;
        }
        System.out.println(Thread.currentThread().getName()+" is getting the product");
        String product = this.list.poll();
        //notify the front end
        JSONObject obj = new JSONObject();
		obj.putOpt("name", this.name);
		obj.putOpt("change", this.list.size()); 
        WSService.notifyFrontend(obj.toString());
        return product;
    }
    @Override
    /**
     * a method that returns the name of the Queue
     */
    public String getName() {
        return this.name;
    }
}
