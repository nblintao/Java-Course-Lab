package browser;

public class BrowseDriver {
	History history;
	public BrowseDriver(History history) {
		this.history = history;
	}
	
	public void browse(String url){
		history.newPage(url);
	}
}
