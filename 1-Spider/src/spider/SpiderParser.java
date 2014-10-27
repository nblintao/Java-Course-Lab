package spider;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

public class SpiderParser {
	static PrintWriter outputStream;
	
	/*
	 * Parse a book.
	 */
	public static void main(String[] argv){
//		String bookname = "sjecl";	// Êé½£¶÷³ðÂ¼
		String bookname = "xsfh";	// Ñ©É½·Éºü
		
		
		try {
			outputStream = new PrintWriter(new FileOutputStream("SpiderParser_" + bookname + ".txt"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		String url = "http://www.my285.com/wuxia/jinyong/"+bookname+"/index.htm";
		// Using htmlParser
		Parser parser = null;
		try {
			parser = new Parser(url);
		} catch (ParserException e) {
			e.printStackTrace();
		}
		NodeList nodelist = null;
		try {
			nodelist = parser.parse(null);
		} catch (ParserException e) {
			e.printStackTrace();
		}
		// Nodelist of html
		nodelist = nodelist.toNodeArray()[0].getChildren();
		
		findTable(nodelist);
		
		outputStream.close();
	}
	/*
	 * Find out all tables in the node list.
	 */
	public static void findTable(NodeList nodelist){
		if(null == nodelist){
			return;
		}
		SimpleNodeIterator iterator = nodelist.elements();
		while(iterator.hasMoreNodes()){
			Node node = iterator.nextNode();
			if(node instanceof TableTag){
				parseTable(node);
			}
			// Do it recursively to its children,
			// even it is a table.
			NodeList childNodeList = node.getChildren();
			findTable(childNodeList);
		}		
	}
	/*
	 * Select the table we need and parse it.
	 */
	public static void parseTable(Node node){
		TableTag table = (TableTag) node;
		// The table we need is larger than 15.
		if(table.getChildren().size() > 15){
			TableRow[] rows = table.getRows();
			for(int i=0;i<rows.length;i++){
				TableRow row = rows[i];
				TableColumn[] columns = row.getColumns();
				for(int j=0;j<columns.length;j++){
					TableColumn column = columns[j];
					Node[] links = column.getChildrenAsNodeArray();
					for(int k=0;k<links.length;k++){
						if(links[k] instanceof LinkTag){
							LinkTag link = (LinkTag) links[k];
							// The title is the context of the link.
							String title = link.toPlainTextString();
							// Message on console
							System.out.println("Start " + title);
							
							// My special format for the beginning of each chapter
							outputStream.println("\r\n\r\n\r\n");
							outputStream.println(title);
							outputStream.println("==================");
							outputStream.println("Parsed by Tao LIN.");
							
							try {
								parsePage(new URL(link.getLink()));
							} catch (MalformedURLException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
	/*
	 * Parse each sub-page in the traditional way,
	 * as it is much easier.
	 */
	public static void parsePage(URL pageURL) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(pageURL.openStream()));
		String buf;
		boolean start = false;
		while(null != (buf=in.readLine())){
			// The passage in contained between <td colspan="2"> and </td>
			// Each paragraph is surrounded by <br>
			if(start){
				if(buf.contains("</td>"))
					break;
				int indexEnd = buf.indexOf("<br>");
				if(indexEnd > 0){
					String para = buf.substring(0,indexEnd);
					outputStream.println(para);
				}
			}
			else if(buf.contains("<td colspan=\"2\">")){
				start = true;
			}
		}

		in.close();
	}
}
