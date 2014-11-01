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
		try {
			sourceURL = new URL(e.getActionCommand());
			BufferedReader in = new BufferedReader(new InputStreamReader(sourceURL.openStream()));
			String buf;
			int count = 0;
			while(!(null==(buf=in.readLine())) && count < 10){
				System.out.println(buf);
				JTextArea text = new JTextArea(buf);
				text.setEditable(false);
				pageView.add(text);
				count++;
			}
			
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
