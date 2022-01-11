package com.prodcons.server.graph;
//memento class to hold the snapshot
public class Memento {
    private Graph graph;
    /**
     * create a memento
     * @param graph 
     */
    public Memento(Graph graph){
        this.graph=graph;
    }
    /**
     * get the graph from a memento
     * @return the graph
     */
    public Graph getState(){
        return this.graph;
    }

}
