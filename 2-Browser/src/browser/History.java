package browser;

import java.util.Vector;

public class History {
	Vector<String> historyVector;
	int currentPage;
	Browse browse;
	History(Browse browse){
		historyVector = new Vector<String>();
		currentPage = -1;
		this.browse = browse;
	}
	public void backward(){
		if(currentPage <= 0){
			System.out.println("Can't go backward!");
		}else{
			currentPage--;
			browse.browseNew(historyVector.get(currentPage));
		}
	}
	public void forward(){
		
	}
	public void newPage(String url){
		
	}
}
