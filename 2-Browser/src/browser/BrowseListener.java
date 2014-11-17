package browser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BrowseListener implements ActionListener {
//	private JPanel pageView;
//	private JEditorPane jep;
//	private int mode;
//	public Browse browse;
	History history;
	
//	public BrowseListener(int mode,JPanel pageView, JEditorPane jep, Browse browse, History history){
	public BrowseListener(History history){
//		this.pageView = pageView;		
//		this.jep = jep;
//		this.mode = mode;
//		browse = new Browse(this);
//		this.browse = browse;
		this.history = history;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
//		System.out.println(e);
		String url = e.getActionCommand();
		history.newPage(url);
/*			
  			pageView.removeAll();
  			if(mode == 0){
				try {
					browse.browseInitial(url, pageView);
					System.out.println(e.getActionCommand() + " is parsed successfully.");
				} catch (Exception e1) {
					e1.printStackTrace();
					pageView.add(new JTextArea("555~ I can't find "+ e.getActionCommand()));
					System.out.println(e.getActionCommand() + " is parsed unsuccessfully.");
				}

			}
			else if(mode == 1){
				try {
					jep.setPage(url);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
				
		pageView.revalidate();
		
*/
		
	}

}
