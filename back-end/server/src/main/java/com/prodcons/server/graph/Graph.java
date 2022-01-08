package com.prodcons.server.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Graph {
    private Map<vertex, ArrayList<vertex>> adjVertices;

    //for machine 
    void addVertex(vertex v, vertex after) {
        adjVertices.putIfAbsent(v, new ArrayList<>(Arrays.asList(after)));
    }
    // for queue
    void addVertex(vertex v, ArrayList<vertex> list)
    {
        
    }
}
