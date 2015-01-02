package lianliankan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerThread extends Thread {
	DataCenter dc;
	Socket client;
	BufferedWriter bw;	
	public ServerThread(DataCenter dc, Socket client) {
		this.dc = dc;
		this.client = client;
	}

	public void run(){
		BufferedReader br;
		
//		ObjectOutputStream oos;
		String line;
		
		System.out.println("Sub-server start");
		try {
			br = new BufferedReader(new InputStreamReader(client.getInputStream()));

			
//			oos = new ObjectOutputStream(client.getOutputStream());
//			oos.writeObject(dc);
//			oos.close();
			
			bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));			
			
			bw.write(dc.getInfo()+'\n');
			bw.flush();
			
//			bw.write("hahaha");
//			bw.flush();
			
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
		switch(cmd[0]){
		case "delete":
			try {
				bw.write(line+'\n');
				bw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}			
			break;			
		default:
			System.out.println("Unknown command");
				
		}
	}
}
