package leafCounter;

import java.util.*;
import java.io.*;
import java.net.URL;

import com.google.gson.*;

public class LeafCounter {
	
	public static void main(String[] args) throws Exception
	{
		String threadUrl = "";
		String board = "";
		String threadId = "";
		String jsonUrl = "";
		
		Scanner input = new Scanner(System.in);
		
		System.out.print("Paste thread url: ");
		threadUrl = input.next();
		
		int cutoffPoint = threadUrl.indexOf("thread");
		board = threadUrl.substring(cutoffPoint-4,cutoffPoint-1);
		threadId = threadUrl.substring(cutoffPoint+7, threadUrl.length());
		//System.out.println(board);
		//System.out.println(threadId);
		
		jsonUrl = "http://a.4cdn.org/"+board+"/thread/"+threadId+".json";
		jsonUrl = readUrl(jsonUrl);
		System.out.println(jsonUrl);
		
		Gson gson = new Gson();
		//ArrayList<Post> posts = new ArrayList<Post>(0);
		Post posts = gson.fromJson(jsonUrl,Post.class);
		//posts.add(gson.fromJson(jsonUrl,Post.class));
		//System.out.println(posts.getCountry());
		
	}
	
	private static String readUrl(String urlString) throws Exception {
	    BufferedReader reader = null;
	    try {
	        URL url = new URL(urlString);
	        reader = new BufferedReader(new InputStreamReader(url.openStream()));
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        return buffer.toString();
	    } finally {
	        if (reader != null)
	            reader.close();
	    }
	}
	
	
}
