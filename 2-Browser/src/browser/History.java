package browser;

import java.util.Vector;

public class History {
	Vector<String> historyVector;
	int currentPage;
	Browse browse;
	History(){
		historyVector = new Vector<String>();
		currentPage = -1;		
	}
	public void setBrowse(Browse browse){
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
		if(currentPage + 1 >= historyVector.size()){
			System.out.println("Can't go forward!");
		}else{
			currentPage++;
			browse.browseNew(historyVector.get(currentPage));
		}
	}
	public void newPage(String url){
		for(int i=currentPage+1;i<historyVector.size();i++){
			historyVector.remove(i);
		}
		historyVector.add(url);
		currentPage++;
		browse.browseNew(historyVector.get(currentPage));
	}
}
