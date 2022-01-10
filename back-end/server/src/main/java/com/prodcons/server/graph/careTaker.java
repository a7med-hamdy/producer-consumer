package com.prodcons.server.graph;

import java.util.ArrayList;

public class careTaker {
    private ArrayList<Memento> memolist = new ArrayList<Memento>();

    public void addMemento(Memento memento){
        memolist.add(memento);
    }

    public Memento getMemento() throws IndexOutOfBoundsException{
        int i =this.memolist.size()-1;
        return memolist.get(i);
    }
}
