package com.prodcons.server;

import com.prodcons.server.graph.machine;
import com.prodcons.server.graph.product;
import com.prodcons.server.graph.waitingList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args){
		SpringApplication.run(ServerApplication.class, args);
		
		machine c2= new machine();
		machine c= new machine();
		
		waitingList lastlist= new waitingList(c2,"fourthList");
		waitingList fourthList= new waitingList(c2,"fourthList");
		c.adding(fourthList);
		waitingList firsList= new waitingList(c,"firsList");
		waitingList secondList= new waitingList(c,"secondList");
		c.addingB(firsList);
		c.addingB(secondList);
		c2.addingB(firsList);
		c2.addingB(secondList);
		for(int i=0;i<10;i++){
			firsList.add();
			secondList.add();
		}

		
	
	}

}
