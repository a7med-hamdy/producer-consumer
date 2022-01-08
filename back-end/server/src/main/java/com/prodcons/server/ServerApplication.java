package com.prodcons.server;

import java.util.ArrayList;
import java.util.Arrays;

import com.prodcons.server.graph.machine;
import com.prodcons.server.graph.waitingList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args){
		SpringApplication.run(ServerApplication.class, args);
		waitingList w1 = new waitingList("Q0");
		waitingList w2 = new waitingList("Q1");
		machine m1 = new machine( new ArrayList<>(Arrays.asList(w1)), "M1",10000);
		machine m2 = new machine( new ArrayList<>(Arrays.asList(w1)), "M2",200);
		machine m3 = new machine( new ArrayList<>(Arrays.asList(w2)), "M3",1);
		m1.setAfter(w2);
		m2.setAfter(w2);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("starting input");

		synchronized(w1){
			w1.add("product");
			w1.add("product2");
		}
		
	}

}

