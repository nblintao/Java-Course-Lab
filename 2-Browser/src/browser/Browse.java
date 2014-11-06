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
	private static int imageCacheCounter = 0;
	static GridBagConstraints s = new GridBagConstraints();
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
				try {
					browseImage((ImageTag)node, panel);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	public void browseParagraph(ParagraphTag paragraph, JPanel panel){
//		System.out.println(paragraph.toString());
//		JLabel text = new JLabel(paragraph.getStringText());
		JTextArea text = new JTextArea(paragraph.getStringText());
		text.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		text.setEditable(false);
//		text.setLineWrap(true);
		panel.add(text);
		
		setGridBagConstraints(text, panel);
		
		panel.revalidate();
		NodeList nodeList = paragraph.getChildren();
		browseTrivial(nodeList, panel);
		
	}
	public void browseImage(ImageTag image, JPanel panel) throws Exception{
		
		
		String urlText = image.getImageURL();
		System.out.println(urlText);
		
		//TODO need advanced hash algorithm
//		String hashedName = urlText.substring(urlText.lastIndexOf('/')+1);
		imageCacheCounter++;
		String hashedName = String.valueOf(imageCacheCounter);
		
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
	public void browseTable(TableTag table, JPanel panel){
		//TODO
	}
	public void setGridBagConstraints(Component in, JPanel out){
        s.fill = GridBagConstraints.HORIZONTAL;
        s.gridwidth = 0;
        s.weightx = 0;
        s.weighty = 0;
        ((GridBagLayout)out.getLayout()).setConstraints(in, s);
	}
}
