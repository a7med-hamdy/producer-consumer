package com.prodcons.server.graph;

public class Originator {
    private Graph graph;
    /**
     * set the state woth the current garph
     * @param graph the current graph
     */
    public void setState(Graph graph){
        this.graph=graph;
    }
    /**
     * @return the graph of the current state
     */ 
    public Graph getState(){
        return this.graph;
    }
    /**
     *create memento using a graph 
     * @return the new memento
     */
    public Memento getMemento(){
        return new Memento(this.graph);
    }
    /**
     * 
     * @param memento
     * @return
     */
    public Graph getStateFromMemento(Memento memento){
        return memento.getState();
    }

}
