package browser;

import java.awt.Font;
import java.awt.TextArea;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

public class Browse {
	public void browseInitial(String url, JPanel pageView){
		Parser parser = null;
		try {
			parser = new Parser(url);
		} catch (ParserException e1) {
			e1.printStackTrace();
		}
		
		NodeList nodeList = null;
		try {
			nodeList = parser.parse(null);
		} catch (ParserException e) {
			e.printStackTrace();
		}
		// nodeList of html
//		nodeList = nodeList.toNodeArray()[0].getChildren();
		
		browseTrivial(nodeList, pageView);
	}
	
	public void browseTrivial(NodeList nodeList, JPanel panel){
		if(null == nodeList)
			return;
		SimpleNodeIterator iterator = nodeList.elements();
		while(iterator.hasMoreNodes()){
			Node node = iterator.nextNode();

			if(node instanceof ParagraphTag){
				browseParagraph((ParagraphTag)node, panel);
			}			
			else if(node instanceof ImageTag){
				browseImage((ImageTag)node, panel);
			}
			else if(node instanceof TableTag){
				browseTable((TableTag)node, panel);
			}
			else{
				NodeList childnodeList = node.getChildren();
				browseTrivial(childnodeList, panel);				
			}
		}
	}
	public void browseParagraph(ParagraphTag paragraph, JPanel panel){
//		System.out.println(paragraph.toString());
		JLabel text = new JLabel(paragraph.getStringText());
		text.setFont(new Font("微软雅黑", Font.PLAIN, 16));
//		text.setEditable(false);
		panel.add(text);
		panel.revalidate();
	}
	public void browseImage(ImageTag image, JPanel panel){
		
	}
	public void browseTable(TableTag table, JPanel panel){
		//TODO
	}
}
