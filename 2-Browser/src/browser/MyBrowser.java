package browser;

import java.awt.*;

import javax.swing.*;


public class MyBrowser extends JFrame{
	private static final long serialVersionUID = -579949100353474747L;
	private static BrowseListener browseListener;
	JPanel pageView;
	
	MyBrowser(){
		super("MyBrowser");
		pageView = new JPanel();
		browseListener = new BrowseListener(pageView);
		
		InitializeFrame();
		InitializeLayout();
		
	}
	public void InitializeFrame(){
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		Toolkit toolkit = getToolkit();
		Dimension dimension = toolkit.getScreenSize();
		setBounds(dimension.width/4,dimension.height/4,dimension.width/2,dimension.height/2); 
		
//		pageView.setBounds(getBounds());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void InitializeLayout(){
		Box navigator = Box.createHorizontalBox();
		
		JButton buttonBackward = new JButton("Backward");
		navigator.add(buttonBackward);

		JButton buttonForward = new JButton("Forward");
		navigator.add(buttonForward);
		
		JButton buttonRefresh = new JButton("Refresh");
		navigator.add(buttonRefresh);

		JTextField urlField = new JTextField();
		urlField.addActionListener(browseListener);
		
		navigator.add(urlField);

		Container content = getContentPane();
		content.setLayout(new BorderLayout());
		
		content.add(navigator, BorderLayout.NORTH);

		pageView.setLayout(new GridLayout(0,1));
		
//		for(int i=0;i<100;i++){
//			JTextArea text = new JTextArea(content.getBounds().width + "" + i);
//			text.setEditable(false);
//			pageView.add(text);
//		}
		
		
		content.add(new JScrollPane(pageView), BorderLayout.CENTER);
	}
	public static void main(String[] argv){
		MyBrowser myBrowser = new MyBrowser();
		System.out.println("Initialize finished.");
		myBrowser.setVisible(true);
	}
}
