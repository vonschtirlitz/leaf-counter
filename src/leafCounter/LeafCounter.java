package leafCounter;

import java.util.*;
import java.io.*;
import java.net.URL;

import com.google.gson.*;

public class LeafCounter {
	
	public static void main(String[] args) throws Exception
	{
		//Init shit
		String threadUrl = "";
		String board = "";
		String threadId = "";
		String jsonUrl = "";
		
		//Get URL from user, dont fuck it up since I havent made exceptions
		Scanner input = new Scanner(System.in);
		System.out.print("Paste thread url: ");
		threadUrl = input.next();
		
		//Trim url to get board and thread id. Board is kind of redundant
		//since this is supposed to be used with /pol/ but I guess it could work with /int/
		int cutoffPoint = threadUrl.indexOf("thread");
		board = threadUrl.substring(cutoffPoint-4,cutoffPoint-1);
		threadId = threadUrl.substring(cutoffPoint+7, threadUrl.length());
		
		//Convert standard 4chan url to a.4cdn url
		jsonUrl = "http://a.4cdn.org/"+board+"/thread/"+threadId+".json";
		jsonUrl = readUrl(jsonUrl);
		System.out.println(jsonUrl);
		
		//Convert json to java data
		Gson gson = new Gson();
		PostData posts = gson.fromJson(jsonUrl,PostData.class);
		System.out.println(posts);
		
		//Count data
		List<CountryData> countries = new ArrayList<CountryData>();
		for(int i=0;i<posts.posts.size();i++){
			boolean finished=false;
			if(countries.size()==0){
				countries.add(new CountryData(posts.getCountry(i)));
				countries.get(0).addId(posts.getId(i));
				finished=true;
			}
			else if(!finished){
				for(int j=0;j<countries.size();j++){
					if(posts.getCountry(i).equals(countries.get(j).getName())){
						for(int k=0;k<countries.get(j).registeredIds.size();k++){
							if(posts.getId(i).equals(countries.get(j).registeredIds.get(k))){
								finished=true;
								}
							
							else if(!finished)
							{
								countries.get(j).addOne();
								countries.get(j).addId(posts.getId(i));
								finished=true;
							}
						}
					}
					if((!(posts.getCountry(i).equals(countries.get(j).getName())))&&(!finished)){
						countries.add(new CountryData(posts.getCountry(i)));
						countries.get(j).addId(posts.getId(i));
						finished=true;
					}
					
				}
			}
		}
		
		//Print Data
		for(int i=0;i<countries.size();i++){
			System.out.println(countries.get(i).getName()+" "+countries.get(i).getNum());
		}
		System.out.println("done");
		
		//TODO actually process and count the leafs, check user ID for unique leafs
		
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
