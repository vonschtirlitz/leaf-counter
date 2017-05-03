package leafCounter;

import java.util.ArrayList;
import java.util.List;

public class PostData {
	public List<Posts> posts = new ArrayList<Posts>();
	
	//public void setPostData(Posts newValue){
	//	posts = newValue;
	//}
	public String getCountry(int index)
	{
		return posts.get(index).getCountry();
	}
	
	public String getId(int index)
	{
		return posts.get(index).getId();
	}
	
	public String toString(){
		return posts.toString();
	}

}
