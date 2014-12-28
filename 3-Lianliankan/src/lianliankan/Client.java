package lianliankan;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;


public class Client{
	JFrame frame;
	DataCenter dc;
	
	Client(){
		frame = new JFrame("Lianliankan");
		dc = new DataCenter();
		InitializeFrame();
		InitializeLayout();
		frame.setVisible(true);
		
		try {
			Socket socket = new Socket("127.0.0.1",9999);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(true){
				String line;line=br.readLine();
				if(line != null)
					System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		
	}
	private void InitializeLayout() {
		Container content = frame.getContentPane();
		
		content.setLayout(new GridLayout(dc.height, dc.width));
		
		for(int i=0;i<dc.height;i++){
			for(int j=0;j<dc.width;j++){
				content.add(new Block(i, j, dc));
			}
		}
		
	}
	public void InitializeFrame(){
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		Toolkit toolkit = frame.getToolkit();
		Dimension dimension = toolkit.getScreenSize();
		frame.setBounds(dimension.width/4,dimension.height/4,dimension.width/2,dimension.height/2); 
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] argv){
		ServerThread serverThread = new ServerThread();
		serverThread.start();
		Client client = new Client();
	}
}
