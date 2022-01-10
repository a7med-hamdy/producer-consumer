package com.prodcons.server;


import com.prodcons.server.graph.Graph;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args){
		SpringApplication.run(ServerApplication.class, args);
		/*Graph g = new Graph();
		g.addMachine();
		g.addMachine();
		g.addMachine();
		g.addMachine();
		g.addQueue();
		g.addQueue();
		g.addQueue();
		g.addQueue();
		g.addEdge("Q0", "M0");
		g.addEdge("M0", "Q1");
		g.addEdge("Q1", "M1");
		g.addEdge("Q1", "M2");
		g.addEdge("M1", "Q2");
		g.addEdge("M2", "Q3");
		g.addEdge("Q2", "M3");
		g.addEdge("Q3", "M3");
		g.addEdge("M3", "Q4");
		// g.startSimulation();
		
		System.out.println(g.getGraphJson());*/

	}

}

