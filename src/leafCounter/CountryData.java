package leafCounter;

import java.util.ArrayList;
import java.util.List;

public class CountryData {
	String countryName;
	int numInThread;
	//List<String> registeredIds= new ArrayList<String>();
	
	public CountryData(String name){
		countryName = name;
		numInThread = 1;
	}
	
	public String getName(){
		return countryName;
	}
	
	public int getNum(){
		return numInThread;
	}
	
	public void addOne(){
		numInThread++;
	}
	
	/*public void addId(String id){
		registeredIds.add(id);
	}*/
	
	/*public List<String> returnIds(){
		return registeredIds;
	}*/

}
