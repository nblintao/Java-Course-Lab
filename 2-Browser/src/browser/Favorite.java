package browser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Favorite implements ActionListener {
	Set<String> folder;
	JButton buttonFavorite;
	Boolean bLike;
	String url;
	
	public Favorite() {
		folder = new HashSet<String>();	
		
		buttonFavorite = new JButton();
		disLike();
		buttonFavorite.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e){
		if(bLike){
			disLike();
			folder.remove(url);
		}else{
			like();
			folder.add(url);
		}
	}

	public void newPage(String newURL) {
		url = newURL;
		if(folder.contains(url)){
			like();
		}else{
			disLike();
		}
	}

	private void disLike() {
		bLike = false;
		buttonFavorite.setIcon(new ImageIcon("./Icon/unfavorite.png"));		
	}
	
	private void like() {
		bLike = true;
		buttonFavorite.setIcon(new ImageIcon("./Icon/favorite.png"));		
	}

	public void loading() {
		buttonFavorite.setIcon(new ImageIcon("./Icon/loading.png"));
	}
	
}
