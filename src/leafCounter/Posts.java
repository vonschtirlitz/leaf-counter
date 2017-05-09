package leafCounter;

public class Posts {
	public String id;
	public String country;
	public String closed;
	
	public void setId(String newValue){
		id = (newValue);
	}
	
	public void setCountry(String newValue){
		country = (newValue);
	}
	
	public String getId(){
		return id;
	}
	
	public String getCountry(){
		return country;
	}
	
	public String getArchived(){
		return closed;
	}
	
	public String toString()
	{
		return ("ID: "+id+" Country: "+country+"; ");
	}
}
