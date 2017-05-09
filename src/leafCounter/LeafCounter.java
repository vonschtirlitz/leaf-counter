package leafCounter;

import java.util.*;
import java.text.*;
import java.io.*;
import java.net.*;
import com.google.gson.*;

public class LeafCounter {
	
	public static void main(String[] args) throws Exception
	{
		//Init shit
		String threadUrl = "";
		String board = "";
		String threadId = "";
		String jsonUrl = "";
		boolean isArchived = false;
		
		//Get URL from user, dont fuck it up since I havent made exceptions
		Scanner input = new Scanner(System.in);
		System.out.print("Paste thread url: ");
		threadUrl = input.next();
		
		//Trim url to get board and thread id. Board is kind of redundant
		//since this is supposed to be used with /pol/ but I guess it could work with /int/
		int cutoffPoint = threadUrl.indexOf("thread");
		board = threadUrl.substring(cutoffPoint-4,cutoffPoint-1);
		
		//MANUAL OVERRIDE
		//board="pol";
		board="bant";
		
		if(threadUrl.charAt(threadUrl.length()-1)=='/'){
			threadId = threadUrl.substring(cutoffPoint+7, threadUrl.length()-1);
		}
		else{
			threadId = threadUrl.substring(cutoffPoint+7, threadUrl.length());
		}
		
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
		List<String> postids = new ArrayList<String>();
		try{
			if(posts.posts.get(0).getArchived().equals("1")){
				isArchived = true;
				System.out.println("WARNING! Thread is archived, inaccurate data regarding unique posts will be displayed.");
			}
		}catch (NullPointerException e){	
		}
		if(!isArchived){
			for(int i=0;i<posts.posts.size();i++){
				boolean idmatches=false;
				for(int j=0;j<postids.size();j++){
					if(posts.getId(i).equals(postids.get(j))){
						idmatches=true;
					}
				}
				if(idmatches==false){
					postids.add(posts.getId(i));
					//check if country exists
					boolean countryexists = false;
					for(int j=0;j<countries.size();j++){
						if(!countryexists){
							if(posts.getCountry(i).equals(countries.get(j).getName()))
							{
								countries.get(j).addOne();
								countryexists = true;
							}
						}
					}
					if(!countryexists){
						countries.add(new CountryData(posts.getCountry(i)));
					}
				}
			}
		}
		else{
			for(int i=0;i<posts.posts.size();i++){
				postids.add(posts.getId(i));
				//check if country exists
				boolean countryexists = false;
				for(int j=0;j<countries.size();j++){
					if(!countryexists){
						if(posts.getCountry(i).equals(countries.get(j).getName()))
						{
							countries.get(j).addOne();
							countryexists = true;
						}
					}
				}
				if(!countryexists){
					countries.add(new CountryData(posts.getCountry(i)));
				}
			}
		}
		
		//Print Data and quantify leafs
		for(int i=0;i<countries.size();i++){
			System.out.println(countries.get(i).getName()+" "+countries.get(i).getNum());
		}
		int leafId = -1;
		int totalPosts = 0;
		for(int i=0;i<countries.size();i++){
			if(countries.get(i).getName().equals("CA")){
				leafId=i;
			}
			totalPosts+=countries.get(i).getNum();
		}
		double leafPercentage = 0;
		if(leafId!=-1){
			leafPercentage = (double)countries.get(leafId).getNum()/(double)totalPosts;
		}
		System.out.print("Leaf clutter in this thread is at "+(leafPercentage*100)+"%");		
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