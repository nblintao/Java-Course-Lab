package browser;

import javax.swing.JTextArea;

public class HyperLink extends JTextArea{
	private static final long serialVersionUID = 1839995194195357740L;
	private String link;
	HyperLink(String linkText, String link){
		super(linkText);
		this.link = link;
	}
	String getLink(){
		return link;
	}
}
