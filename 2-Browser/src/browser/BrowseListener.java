package browser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class BrowseListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		URL sourceURL;
		try {
			sourceURL = new URL(e.getActionCommand());
			BufferedReader in = new BufferedReader(new InputStreamReader(sourceURL.openStream()));
			String buf;
			while(!(null==(buf=in.readLine()))){
				System.out.println(buf);
				
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
