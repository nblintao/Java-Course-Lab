package browser;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

public class Browse {
	private static int imageCacheCounter = 0;
	static BrowseListener browseListener;
	static GridBagConstraints s = new GridBagConstraints();
	PageView pageView;
	JScrollPane jsp;
	History history;
	Status status;
	
	public Browse(BrowseListener browseListener, PageView pageView, JScrollPane jsp, History history, Status status){
		Browse.browseListener = browseListener;
		this.pageView = pageView;
		this.jsp = jsp;
		this.history = history;
		this.status = status;
	}
	
	public void browseNew(String url){
		pageView.removeAll();
//		System.out.println(jsp.getVerticalScrollBar().getValue());
//		jsp.getVerticalScrollBar().setValue(200);
//		jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMinimum());
		try {
			browseInitial(url, pageView);
//			System.out.println(url + " is parsed successfully.");
			status.newInfo(url + " is parsed successfully.");
		} catch (Exception e) {
			pageView.add(new JTextArea("555~ I can't find "+ url));
			e.printStackTrace();
		}
		pageView.revalidate();
	}
	
	public void browseInitial(String url, JPanel pageView) throws Exception{
		Parser parser = null;
		parser = new Parser(url);
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
			if(node instanceof TextNode){
				browseText((TextNode)node, panel);
			}
			else if(node instanceof LinkTag){
				browseLink((LinkTag)node, panel);
			}
			else if(node instanceof ParagraphTag){
				browseParagraph((ParagraphTag)node, panel);
			}
			else if(node instanceof ImageTag){
				try {
					browseImage((ImageTag)node, panel);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(node instanceof ScriptTag){
				// TODO Ignore script now
			}
//			else if(node instanceof TableTag){
//				browseTable((TableTag)node, panel);
//			}
			else{
				NodeList childnodeList = node.getChildren();
				browseTrivial(childnodeList, panel);				
			}
		}
	}
	private void browseLink(LinkTag node, JPanel panel) {

		HyperLink text = new HyperLink(node.getLinkText(),node.getLink());
		text.setFont(new Font("微软雅黑", Font.ITALIC, 16));
		text.setEditable(false);
		text.setLineWrap(true);
		text.addMouseListener(new LinkListener(text, history, status));
		panel.add(text);
		setGridBagConstraints(text, panel);
		panel.revalidate();	
		
		NodeList childnodeList = node.getChildren();
		if(childnodeList != null){
//			browseTrivial(childnodeList, panel);	
			
			SimpleNodeIterator iterator = childnodeList.elements();
			while(iterator.hasMoreNodes()){
				Node subNode = iterator.nextNode();
				if(subNode instanceof ImageTag){
					try {
						browseImage((ImageTag)subNode, panel);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
		}
	}

	public void browseText(TextNode textNode, JPanel panel){
		if(textNode.getText().trim().length()==0)
			return;
//		else 
//			System.out.println(textNode.getText());
//		System.out.println(textNode.getText().trim().length());
		
		JTextArea text = new JTextArea(textNode.getText());
		text.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		text.setEditable(false);
		text.setLineWrap(true);
		panel.add(text);
		setGridBagConstraints(text, panel);
		panel.revalidate();
		
	}
	public void browseParagraph(ParagraphTag paragraph, JPanel panel){

//		NodeList nodeList = paragraph.getChildren();
//		if(null == nodeList)
//			return;
//		SimpleNodeIterator iterator = nodeList.elements();
//		while(iterator.hasMoreNodes()){
//			Node node = iterator.nextNode();
//			if(node instanceof TextNode){
//				browseText((TextNode)node, panel);
//			}
//		}
		
		NodeList nodeList = paragraph.getChildren();
		browseTrivial(nodeList, panel);		
	}
	public void browseImage(ImageTag image, JPanel panel) throws Exception{
		
		
		String urlText = image.getImageURL();
//		System.out.println(urlText);
		status.newInfo(urlText);
		if(urlText.substring(urlText.lastIndexOf('.')).equals(".bmp")){
//			System.out.println("I don't know bmp image!");
			status.newInfo("I don't know bmp image!");
		}else{
			//TODO need advanced hash algorithm
			// String hashedName = urlText.substring(urlText.lastIndexOf('/')+1);
			imageCacheCounter++;
			String hashedName = String.valueOf(imageCacheCounter)+urlText.substring(urlText.lastIndexOf('.'));
			
			URL url = new URL(urlText);
			InputStream is = url.openStream();
			File imageFile = new File("./ImageCache/" + hashedName);
			
			OutputStream os = new FileOutputStream(imageFile);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while((bytesRead = is.read(buffer,0,8192))!=-1)
				os.write(buffer,0,bytesRead);
			os.close();
			JLabel imageLabel = new JLabel();
			imageLabel.setIcon(new ImageIcon(imageFile.getAbsolutePath()));
			panel.add(imageLabel);
			
			setGridBagConstraints(imageLabel, panel);
			
			panel.revalidate();
		}
	    
	}
	public void browseTable(TableTag table, JPanel panel){
		//TODO
	}
	public void setGridBagConstraints(Component in, JPanel out){
        s.fill = GridBagConstraints.HORIZONTAL;
        s.gridwidth = 0;
        s.weightx = 1;
//        s.weighty = 0;
        ((GridBagLayout)out.getLayout()).setConstraints(in, s);
	}
}
