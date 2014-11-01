package browser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class BrowseListener implements ActionListener {
	private JPanel pageView;
	BrowseListener(JPanel pageView){
		this.pageView = pageView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		URL sourceURL;
		pageView.removeAll();
		try {
			sourceURL = new URL(e.getActionCommand());
			BufferedReader in = new BufferedReader(new InputStreamReader(sourceURL.openStream()));
			String buf;
			while(!(null==(buf=in.readLine()))){
				System.out.println(buf);
				JTextArea text = new JTextArea(buf);
				text.setEditable(false);
				pageView.add(text);
			}			
			
		} catch (Exception e1) {
			pageView.add(new JTextArea("555~ I can't find "+ e.getActionCommand()));
		}
		
		pageView.revalidate();
	}

}
