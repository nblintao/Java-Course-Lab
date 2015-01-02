package lianliankan;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;


public class ClientThread extends Thread {
	JFrame frame;
//	DataCenter dc;
	LocalDataCenter ldc;
	Socket socket;
	BufferedReader br;
	BufferedWriter bw;
	boolean onGoing;
	
	ClientThread(){
		onGoing = false;
		InitializeSocket();
		start();
	}
	public void run(){
		String line;
		while(true){
			try {
				line=br.readLine();
				if(line != null)
					ReceiveCommand(line);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	private void ReceiveCommand(String line) {
		String[] cmd = line.split(" ");
		switch(cmd[0]){
		case "GameInitialize":
			GameInitialize(cmd);
			break;
		case "delete":
			Delete(cmd);
			break;
		case "score":
			updateScore(cmd);
		}
	}
	private void updateScore(String[] cmd) {
		int n = Integer.parseInt(cmd[1]);
		String dispScore = "";
		for(int i=0;i<n;i++){
			int id = Integer.parseInt(cmd[2*i + 2]);
			int score = Integer.parseInt(cmd[2*i + 3]);
			dispScore += id + ": " + score +"   ";
		}
		ldc.theScore.setText(dispScore);
	}
	private void Delete(String[] cmd) {
		if(onGoing==false){
			System.out.println("The game hasn't been initialized.");
			return;
		}
		int xi,xj,yi,yj;
		xi = Integer.parseInt(cmd[1]);
		xj = Integer.parseInt(cmd[2]);
		yi = Integer.parseInt(cmd[3]);
		yj = Integer.parseInt(cmd[4]);
		ldc.pullMapFalse(xi,xj,yi,yj);
	}
	private void GameInitialize(String[] cmd) {
		InitializeDataCenter(cmd);
		InitializeFrame();
		InitializeLayout();
		frame.setVisible(true);
		onGoing = true;
	}
	private void InitializeDataCenter(String[] cmd) {
//		ObjectInputStream ois;
//		ois = new ObjectInputStream(socket.getInputStream());
//		DataCenter dc = (DataCenter) ois.readObject();
//		ois.close();
		ldc = new LocalDataCenter(cmd,bw);
	}
	private void InitializeSocket() {
		try {
			socket = new Socket("127.0.0.1",9999);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void InitializeLayout() {
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());
		
		Container mainContent = new Container();
		mainContent.setLayout(new GridLayout(ldc.height, ldc.width));		
		for(int i=0;i<ldc.height;i++){
			for(int j=0;j<ldc.width;j++){				
				mainContent.add(ldc.blockCollection[i][j]);
			}
		}
		content.add(mainContent, BorderLayout.CENTER);
		
		JMenuBar bar = new JMenuBar();
		JLabel theScore = new JLabel("Game Start!");
		ldc.theScore = theScore;
		bar.add(theScore);
		
		content.add(bar, BorderLayout.SOUTH);		
	}
	public void InitializeFrame(){
		frame = new JFrame("Lianliankan");
		
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		Toolkit toolkit = frame.getToolkit();
		Dimension dimension = toolkit.getScreenSize();
		frame.setBounds(dimension.width/4,dimension.height/4,dimension.width/2,dimension.height/2); 
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] argv){
		new ServerMainThread();
		new ClientMainThread();
	}
}
