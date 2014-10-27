package spider;

import java.net.*;
import java.io.*;

public class Spider {
	public static void main(String argv[]) throws Exception {
		
		String bookname = "sjecl";	// 书剑恩仇录
//		String bookname = "xsfh";	// 雪山飞狐
		
		
		
		PrintWriter outputStream = new PrintWriter(new FileOutputStream(bookname + ".txt"));
		
		parseIndex(new URL("http://www.my285.com/wuxia/jinyong/"+bookname+"/index.htm"), outputStream);
				
		outputStream.close();
		
		System.out.println("Finished.");
		
	}
	/*
	 * Parse the index page of sourceURL and the output is to outputStream
	 */
	public static void parseIndex(URL sourceURL, PrintWriter outputStream) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(sourceURL.openStream()));
		String buf;
		while(!(null==(buf=in.readLine()))){
			int cursor = 0;
			
			// It is a start if there is a hypertext reference.
			cursor = buf.indexOf("<a href=",cursor);
			
			// Get the title of the chapter,
			// the titles begin with "第"("第X回") or "后"("后记")
			int startTitle = buf.indexOf("第");
			if(startTitle == -1)
				startTitle = buf.indexOf("后");
			
			if(startTitle == -1)
				continue;
			
			int endTitle = buf.indexOf("</a>");
			if(endTitle <= startTitle)
				continue;
					
			// The title of the chapter			
			String title = buf.substring(startTitle, endTitle);
			
			// Message on console
			System.out.println("Start " + title);
			
			// My special format for the beginning of each chapter
			outputStream.println("\r\n\r\n\r\n");
			outputStream.println(title);
			outputStream.println("==================");
			outputStream.println("Parsed by Tao LIN.");
			
			// Find out each hypertext reference, and parse it.
			while(cursor != -1){
				int left = buf.indexOf('"',cursor) + 1;
				int right = buf.indexOf('"',left);
				String href = buf.substring(left, right);
				// Check the href is what we need
				// (it should begin with a digit)
				if(Character.isDigit(href.charAt(0)) == true){
					// Get the new URLs and parse each other using parsePage()
					URL subURL = new URL(sourceURL, href);
					parsePage(subURL, outputStream);
				}
				cursor = right;
				cursor = buf.indexOf("<a href=",cursor);				
			}
		}
		in.close();		
	}
	/*
	 * Get the context in each page
	 */
	public static void parsePage(URL pageURL, PrintWriter outputStream) throws Exception {
//		System.out.println(pageURL.toString());
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
	
	public static void dispAll (URL sourceURL) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(sourceURL.openStream()));
		String buf;
		while(!(null==(buf=in.readLine()))){
			System.out.println(buf);
		}	
	}
}
