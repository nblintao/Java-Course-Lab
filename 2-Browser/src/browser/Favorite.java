package browser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;

public class Favorite implements ActionListener {
	Set<String> folder;
	JButton buttonFavorite;
	Boolean bLike;
	String url;
	JMenu menuFolder;
	Map<String, FavoriteItem> map;
	BrowseDriver browseDriver;
	
	
	public Favorite(BrowseDriver browseDriver) {
		folder = new HashSet<String>();	
		
		buttonFavorite = new JButton();
		showDisLike();
		buttonFavorite.addActionListener(this);
		
		menuFolder= new JMenu();
		menuFolder.setIcon(new ImageIcon("./Icon/folder.png"));
		
		map = new HashMap<String, FavoriteItem>();
		
		this.browseDriver = browseDriver;

		InitializeFolder();
	}

	private void InitializeFolder() {
		like("http://10.214.47.99:8080/masm/index.html");//非常天空
		like("http://bksy.zju.edu.cn/");//本科生院
		like("http://cspo.zju.edu.cn/");//计算机学院
		like("http://ds.eywedu.com/jinyong/tlbb/");//天龙八部
		like("http://www.cad.zju.edu.cn/home/vagblog/");//VAG小组
	}

	public void actionPerformed(ActionEvent e){
		if(bLike){
			showDisLike();
			disLike(url);			
		}else{
			showLike();
			like(url);
		}
	}

	private void disLike(String url) {
		folder.remove(url);
		FavoriteItem item = map.get(url);
		menuFolder.remove(item);
	}

	private void like(String url) {
		folder.add(url);
		FavoriteItem item = new FavoriteItem(url, browseDriver);
		menuFolder.add(item);
		map.put(url, item);
	}

	public void newPage(String newURL) {
		url = newURL;
		if(folder.contains(url)){
			showLike();
		}else{
			showDisLike();
		}
	}

	private void showDisLike() {
		bLike = false;
		buttonFavorite.setIcon(new ImageIcon("./Icon/unfavorite.png"));		
	}
	
	private void showLike() {
		bLike = true;
		buttonFavorite.setIcon(new ImageIcon("./Icon/favorite.png"));		
	}

	public void loading() {
		buttonFavorite.setIcon(new ImageIcon("./Icon/loading.png"));
	}
	
}
