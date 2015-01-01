package lianliankan;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;


public class Client{
	JFrame frame;
//	DataCenter dc;
	LocalDataCenter ldc;
	Socket socket;
	BufferedReader br;
	BufferedWriter bw;
	
	Client(){
		frame = new JFrame("Lianliankan");
		InitializeSocket();
		InitializeDataCenter();
		InitializeFrame();
		InitializeLayout();
		frame.setVisible(true);
		
		String line;
		while(true){
			try {
//				System.out.println("000000");
				line=br.readLine();
//				System.out.println("111111");
				if(line != null)
					System.out.println("aaaa "+line);
			} catch (IOException e) {
				System.out.println("222222");
				e.printStackTrace();
			}
		}
	}
	private void InitializeDataCenter() {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			DataCenter dc = (DataCenter) ois.readObject();
//			ois.close();
			ldc = new LocalDataCenter(dc,bw);		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		
		content.setLayout(new GridLayout(ldc.height, ldc.width));
		
		for(int i=0;i<ldc.height;i++){
			for(int j=0;j<ldc.width;j++){
				content.add(new Block(i, j, ldc));
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
		ServerMainThread serverMainThread = new ServerMainThread();
		serverMainThread.start();
		Client client = new Client();
	}
}
