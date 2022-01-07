package com.prodcons.server.graph;

import java.util.ArrayList;

public class machine implements vertex {
    ArrayList<waitingList> befoList=new ArrayList<waitingList>();
    waitingList after;

    public void adding(waitingList after){
        System.out.println(" herer erer");
        this.after=after;
    }
    public void addingB(waitingList after){
        this.befoList.add(after);
    }
    public void noter(Boolean z){
        
        for(waitingList i : befoList){
            System.out.println(z);
            i.update(z);
        }
    }

    synchronized public void process(String z,String string) {
        noter(false);
         // making a random number for id
         int min=500,max=3000;
         int x=(int)Math.floor(Math.random()*(max-min+1)+min);
        try{
            Thread.sleep(x);
        }catch(Exception e){System.out.println(e);}
        System.out.println(x);
        System.out.println(z);
        if(this.after!=null){
        this.after.add();
        this.after.show();
        }
        noter(true);

    }
}
