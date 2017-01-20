package main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.Callable;

public class Tester implements Callable<Integer>{
	private String host;
	private int port;
	
	public Tester(String host, int port){
		this.host = host;
		this.port = port;
	}
	@Override
	public Integer call() throws Exception {
		try(Socket socket = new Socket()){
			socket.connect(new InetSocketAddress(host, port), 200);
			//Thread.sleep(100);
			return port;
		}catch(IOException e){
			return -1;
		}
	}
}
