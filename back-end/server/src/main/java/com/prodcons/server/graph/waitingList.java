package com.prodcons.server.graph;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import com.prodcons.server.websocket.WSService;

import org.json.JSONObject;


public class waitingList implements vertex{
    public String name;
    private Queue<String> list= new LinkedList<String>();
    private ArrayList<observer> subscribers = new ArrayList<observer>();
    public waitingList(String name) {
        this.name = name;
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
        JSONObject obj = new JSONObject();
		obj.putOpt("name", this.name);
		obj.putOpt("change", this.list.size()); 
        WSService.notifyFrontend(obj.toString());
    }

    public String getProduct()
    {
        if(this.list.size() == 0)
        {
            return null;
        }
        System.out.println(Thread.currentThread().getName()+" is getting the product");
        String product = this.list.poll();
        JSONObject obj = new JSONObject();
		obj.putOpt("name", this.name);
		obj.putOpt("change", this.list.size()); 
        WSService.notifyFrontend(obj.toString());
        return product;
    }
    @Override
    public String getName() {
        return this.name;
    }
}
