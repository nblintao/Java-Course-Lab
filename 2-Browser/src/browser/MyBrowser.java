package browser;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;


public class MyBrowser extends JFrame{
	private static final long serialVersionUID = -579949100353474747L;
	JEditorPane jep;
	JScrollPane jsp;
	PageView pageView;
	Browse browse;
	History history;
	Status status;
	Address address;
	Favorite favorite;
	BrowseDriver browseDriver;
	
	int mode = 0;// mode 0:My parser  1:JEditorPane
	
	MyBrowser(){
		super("MyBrowser");
		status = new Status();
		pageView = new PageView();
		jep = new JEditorPane();
		jsp = new JScrollPane(pageView);
		history = new History(status);
		browseDriver = new BrowseDriver(history);
		favorite = new Favorite(browseDriver);
		address = new Address(history);
		browse = new Browse(pageView, jsp, history, status, address, favorite);
		history.setBrowse(browse);
		
		InitializeFrame();
		InitializeLayout();
		
		File file = new File("./ImageCache");
		if  (!file.exists() && !file.isDirectory()) 
			file.mkdir();
		
	}
	public void InitializeFrame(){
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		Toolkit toolkit = getToolkit();
		Dimension dimension = toolkit.getScreenSize();
		setBounds(dimension.width/4,dimension.height/4,dimension.width/2,dimension.height/2); 
//		System.out.println(dimension.width+" "+dimension.height);
//		1366 768
		
//		pageView.setBounds(getBounds());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void InitializeLayout(){
//		Box navigator = Box.createHorizontalBox();
		JMenuBar navigator = new JMenuBar();
		
		JButton buttonBackward = new JButton();
		buttonBackward.setIcon(new ImageIcon("./Icon/backward.png"));
		buttonBackward.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				history.backward();
			}
		});
		navigator.add(buttonBackward);

		JButton buttonRefresh = new JButton("");
		buttonRefresh.setIcon(new ImageIcon("./Icon/refresh.png"));
		buttonRefresh.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				history.refresh();
			}			
		});		
		navigator.add(buttonRefresh);
		
		JButton buttonForward = new JButton();
		buttonForward.setIcon(new ImageIcon("./Icon/forward.png"));
		buttonForward.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				history.forward();
			}
		});
		navigator.add(buttonForward);
		
		navigator.add(address.urlField);
		
		navigator.add(favorite.menuFolder);
		
		navigator.add(favorite.buttonFavorite);

		Container content = getContentPane();
		content.setLayout(new BorderLayout());
		
		content.add(navigator, BorderLayout.NORTH);


		if (mode == 0){
			pageView.setLayout(new GridBagLayout());
	//		.getScrollableTracksViewportWidth()
			content.add(jsp, BorderLayout.CENTER);			
		}
		else if(mode == 1){
			jep.setContentType("text/html");
			jep.setEditable(false);
			content.add(new JScrollPane(jep), BorderLayout.CENTER);			
		}

		content.add(status.statusField, BorderLayout.SOUTH);
		

	}
	public static void main(String[] argv){
		MyBrowser myBrowser = new MyBrowser();
//		System.out.println("Initialize finished.");
		myBrowser.status.newInfo("Initialize finished.");
		myBrowser.setVisible(true);
		
		myBrowser.history.newPage("http://www.cad.zju.edu.cn/home/vagblog/");
	}
}
