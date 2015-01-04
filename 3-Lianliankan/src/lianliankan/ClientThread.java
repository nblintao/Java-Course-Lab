package lianliankan;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;

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
			break;
		case "giveUpinfo":
			updateGiveUpInfo(cmd[1],cmd[2]);
			break;
		case "close":
			this.frame.dispose();
			break;
		case "finish":
			finish(Integer.parseInt(cmd[1]));
		}
	}
	private void finish(int max) {
		if(ldc.scoreList.get(ldc.id) == max){
			ldc.overButton.setText("Win!");
		}else{
			ldc.overButton.setText("Lose!");
		}
	}
	private void updateGiveUpInfo(String cmd, String cmd2) {
		String msg = cmd + "/" + cmd2;
		if(ldc.giveUp)
			ldc.overButton.setText(msg);
		else
			ldc.overButton.setText("GiveUp("+msg+")");
	}
	private void updateScore(String[] cmd) {
		ldc.scoreList = new HashMap<Integer, Integer>();
		int n = Integer.parseInt(cmd[1]);
//		String dispScore = "";
		for(int i=0;i<n;i++){
			int id = Integer.parseInt(cmd[2*i + 2]);
			int score = Integer.parseInt(cmd[2*i + 3]);
//			dispScore += id + ": " + score +"   ";
			ldc.scoreList.put(id, score);
		}
//		ldc.theScore.setText(dispScore);
		ldc.updateScoreDisplay();
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
		bar.setLayout(new FlowLayout());
		
		@SuppressWarnings("serial")
		class FontLabel extends JLabel{
			FontLabel(String s, boolean bold){
				super(s);
				if(bold)
					this.setFont(new Font("微软雅黑",Font.BOLD,16));
				else
					this.setFont(new Font("微软雅黑",Font.PLAIN,16));					
			}
		}
		
		bar.add(new FontLabel("My ID: ",true));

		bar.add(new FontLabel(""+ldc.id, false));

		bar.add(new FontLabel("    My socre: ", true));
		
		ldc.myScore = new FontLabel("Game Start!", false);
		bar.add(ldc.myScore);
		
		bar.add(new FontLabel("    Others' socres: ", true));
		ldc.otherScore = new FontLabel("Game Start!", false);
		bar.add(ldc.otherScore);
		
		ldc.overButton = new OverButton(ldc);
		bar.add(ldc.overButton);
		
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
		Stop stop = new Stop();
		new ServerMainThread(stop);
		new ClientMainThread();
//		while(stop.b){
//			break;
//		}
//		return;
	}
}
