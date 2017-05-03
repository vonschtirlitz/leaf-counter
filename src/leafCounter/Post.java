package leafCounter;

public class Post {
	private Id id;
	private Country country;
	
	public void setId(String newValue){
		id.setId(newValue);
	}
	
	public void setCountry(String newValue){
		country.setCountry(newValue);
	}
	
	public String getId(){
		return id.getId();
	}
	
	public String getCountry(){
		return country.getCountry();
	}
	
	public String toString()
	{
		return ("ID: "+id.toString()+" Country: "+country.toString()+"; ");
	}
}
