package com.prodcons.server.graph;

import java.util.ArrayList;

public class careTaker {
    private ArrayList<Memento> memolist = new ArrayList<Memento>();

    public void addMemento(Memento memento){
        memolist.add(memento);
    }

    public Memento getMemento(int index){
        return memolist.get(index);
    }
}
