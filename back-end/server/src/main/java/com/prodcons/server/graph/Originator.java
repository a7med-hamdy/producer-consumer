package com.prodcons.server.graph;

public class Originator {
    private Graph graph;

    public void setState(Graph graph){
        this.graph=graph;
    }
     
    public Graph getState(){
        return this.graph;
    }

    public Memento getMemento(){
        return new Memento(this.graph);
    }

    public Graph getStateFromMemento(Memento memento){
        return memento.getState();
    }

}
