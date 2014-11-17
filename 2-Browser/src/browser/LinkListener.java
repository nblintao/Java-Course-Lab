package browser;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextArea;

public class LinkListener implements MouseListener{
	public JTextArea text;
	History history;
	private Status status;
	LinkListener(JTextArea text, History history, Status status){
		this.text = text;
		this.history = history;
		this.status = status;
	}
	@Override
	public void mouseClicked(MouseEvent event) {
		HyperLink hyperLink = (HyperLink) event.getSource();
		
		String url = hyperLink.getLink();
		history.newPage(url);
//		browse.browseNew(url);
//		browseListener.actionPerformed(new ActionEvent(new Object(), 328952, url));
		
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		text.setFont(new Font("微软雅黑", Font.ITALIC + Font.BOLD, 16));
		
		HyperLink hyperLink = (HyperLink) event.getSource();		
		String url = hyperLink.getLink();
		status.newInfo("HyperLink: " + url);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		text.setFont(new Font("微软雅黑", Font.ITALIC, 16));
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}	
}
