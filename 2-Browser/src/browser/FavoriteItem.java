package browser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

public class FavoriteItem extends JMenuItem implements ActionListener{
	private static final long serialVersionUID = -6413216607837704855L;
	String url;
	BrowseDriver browseDriver;
	public FavoriteItem(String url, BrowseDriver browseDriver) {
		this.url = url;
		this.setText(url);
		this.browseDriver = browseDriver;
		this.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		browseDriver.browse(url);
	}

}
