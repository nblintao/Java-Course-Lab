package lianliankan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Iterator;

public class ServerThread extends Thread {
	DataCenter dc;
	Socket client;
	BufferedWriter bw;
	int id;
	int score;
	static int nextId = 0;
	boolean giveUp;
	
	public ServerThread(DataCenter dc, Socket client) {
		this.dc = dc;
		this.client = client;
		id = nextId++;
		score = 0;
		giveUp = false;
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

			bw.write(dc.getInfo(id)+'\n');
			bw.flush();
			
			while(true){
				line=br.readLine();
				if(line != null){
					executeInput(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void executeInput(String line) {
		System.out.println("Server receive: "+line);
		String[] cmd = line.split(" ");
		switch(cmd[0]){
		case "delete":
			deletePair(line);
			break;
		case "giveUp":
			sayGiveUp();
			break;
		default:
			System.out.println("Unknown command");				
		}
	}

	private void sayGiveUp() {
		giveUp = true;
		
		int count=0;
		int all=0;
		Iterator<ServerThread> it = dc.serverThreadList.iterator();
		while(it.hasNext()){
			if(it.next().giveUp)
				count++;
			all++;
		}
		if(count==all){
			giveUpGame();
		}else{
			sendToAll("giveUpinfo " + count + " " + all);
		}
	}

	private void giveUpGame() {
//		dc.smt.stop.b = true;
		sendToAll("close");
	}

	private void deletePair(String line) {
		sendToAll(line);
		score += dc.scoreForPair;
		sendToAll(dc.getScoreInfo());
	}

	private void sendToAll(String line) {
		System.out.println("Send to all: "+line);
		Iterator<ServerThread> it = dc.serverThreadList.iterator();
		while(it.hasNext()){
			BufferedWriter bw = it.next().bw;
			try {
				bw.write(line+'\n');
				bw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
	}
}
