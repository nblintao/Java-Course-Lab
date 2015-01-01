package lianliankan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {
	DataCenter dc;
	Socket client;
	public ServerThread(DataCenter dc, Socket client) {
		this.dc = dc;
		this.client = client;
		start();
	}

	public void run(){
		//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		BufferedReader br;		
		ObjectOutputStream oos;
		String line;
		
		System.out.println("Server start");
		try {
			br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			oos = new ObjectOutputStream(client.getOutputStream());
			oos.writeObject(dc);
			
			while(true){
				line=br.readLine();
				if(line != null){
					ExecuteInput(line);
				}
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void ExecuteInput(String line) {
		System.out.println(line);
		String[] cmd = line.split(" ");
		switch(line){
		case "delete":
			
			break;
			
		default:
			System.out.println("Unknown command");
				
		}
	}
}
