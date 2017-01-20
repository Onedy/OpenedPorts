package main;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Main {
	private static final String HOST = "180.102.54.151";
	private static final int FIRST_PORT = 20;
	private static final int LAST_PORT = 65536;
	
	public static void main(String[] args){
		ExecutorService es = Executors.newFixedThreadPool(5);
		Set<Future<Integer>> set = new LinkedHashSet<Future<Integer>>();
		System.out.println("Iniciando búsqueda con el puerto "+FIRST_PORT);
		for(int port=FIRST_PORT/*1025*/; port<LAST_PORT/*(1<<16)*/; port+=1){
			//System.out.println("Añadiendo puerto "+port);
			set.add(es.submit(new Tester(HOST, port)));
		}
		int port;
		for (Future<Integer> future : set) {
			try {
				port = future.get();
				if(port > -1){
					System.out.println("Puerto "+port+" abierto!");
				}
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
	    }
		try {
			es.shutdown();
			es.awaitTermination(1, TimeUnit.SECONDS);
			System.out.println("Finalizada búsqueda en el puerto "+(LAST_PORT-1));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
