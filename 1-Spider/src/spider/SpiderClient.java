package spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class SpiderClient{
	public static void main(String[] args) throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet("http://www.my285.com/wuxia/jinyong/xsfh");
		CloseableHttpResponse response = httpclient.execute(httpget);
		try {
	        HttpEntity entity = response.getEntity(); 
            InputStream inputstream = entity.getContent(); 
    		BufferedReader in = new BufferedReader(new InputStreamReader(inputstream));
    		String buf;
    		while(!(null==(buf=in.readLine()))){
    			System.out.println(buf);
    		}

            httpget.abort();
		} finally {
			response.close();
		}
 

	}
}
