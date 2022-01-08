package com.prodcons.server.graph;

public class Memento {
    private Graph graph;

    public Memento(Graph graph){
        this.graph=graph;
    }

    public Graph getState(){
        return this.graph;
    }

}
