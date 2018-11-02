package ImageRecognition;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class testURL {
	public static void main(String args[]) {
		String str_url = "https://gateway.watsonplatform.net/visual-recognition/api/v3/classifiers/dogs_1818370440?version=2018-03-19";
		
		URL url;
		try {
			url = new URL(str_url);
			URLConnection connection = url.openConnection();
			
			//connection.setDoOutput(true);
			//connection.setRequestMethod("GET");
		    
		    
			String username = "apikey";
			String password = "kSNDmdLt2ZVJsbqpWbP_Rd0bfDp_laplkz2pmARGxXvi";
			String input = username + ":" + password;
			Base64.Encoder encoder = Base64.getEncoder();
			String encoding = encoder.encodeToString(input.getBytes(StandardCharsets.UTF_8));
			connection.setRequestProperty("Authorization", "Basic " + encoding);
			
			connection.connect();
			
			Map<String, List<String>> headers = connection.getHeaderFields();
			for(Map.Entry<String, List<String>> entry: headers.entrySet()) {
				String key = entry.getKey();
				for(String value: entry.getValue())
					System.out.println(key + ":" + value);
			}
			
			System.out.println("------------------");
			
			String contentencoding = connection.getContentEncoding();
			if(contentencoding == null) contentencoding = "UTF-8";
			try(Scanner in = new Scanner(connection.getInputStream(), contentencoding)){
				while(in.hasNextLine()) {
					System.out.println(in.nextLine());
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
