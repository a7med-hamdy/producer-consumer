package com.prodcons.server.graph;

import java.util.ArrayList;

public class careTaker {
    private ArrayList<Memento> memolist = new ArrayList<Memento>();
    /**
     * add memento to the list
     * @param memento the memento
     */
    public void addMemento(Memento memento){
        memolist.add(memento);
    }
    /**
     * get the last memento
     * @return the last memento
     * @throws IndexOutOfBoundsException if the list is empty
     */
    public Memento getMemento() throws IndexOutOfBoundsException{
        int i =this.memolist.size()-1;
        return memolist.get(i);
    }
}
