package browser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class BrowseListener implements ActionListener {
	private JPanel pageView;
	public static Browse browse;
	
	BrowseListener(JPanel pageView){
		this.pageView = pageView;
		browse = new Browse();		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String url = e.getActionCommand();
		pageView.removeAll();
		try {
//			URL sourceURL = new URL(e.getActionCommand());
//			BufferedReader in = new BufferedReader(new InputStreamReader(sourceURL.openStream()));
//			String buf;
//			while(!(null==(buf=in.readLine()))){
//				System.out.println(buf);
//				JTextArea text = new JTextArea(buf);
//				text.setEditable(false);
//				pageView.add(text);
//				
//			}

			browse.browseInitial(url, pageView);
			
			System.out.println(e.getActionCommand() + " is parsed successfully.");
		} catch (Exception e1) {
			pageView.add(new JTextArea("555~ I can't find "+ e.getActionCommand()));
			System.out.println(e.getActionCommand() + " is parsed unsuccessfully.");
		}
		pageView.revalidate();
	}

}
