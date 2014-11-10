package browser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class BrowseListener implements ActionListener {
	private JPanel pageView;
	private JEditorPane jep;
	private int mode;
	public static Browse browse;
	
	BrowseListener(int mode,JPanel pageView, JEditorPane jep){
		this.pageView = pageView;		
		this.jep = jep;
		this.mode = mode;
		browse = new Browse(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		System.out.println(e);
		String url = e.getActionCommand();
		pageView.removeAll();
			if(mode == 0){
				try {
					browse.browseInitial(url, pageView);
					System.out.println(e.getActionCommand() + " is parsed successfully.");
				} catch (Exception e1) {
					e1.printStackTrace();
					pageView.add(new JTextArea("555~ I can't find "+ e.getActionCommand()));
					System.out.println(e.getActionCommand() + " is parsed unsuccessfully.");
				}

			}
			else if(mode == 1){
				try {
					jep.setPage(url);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
				
		

		pageView.revalidate();
	}

}
