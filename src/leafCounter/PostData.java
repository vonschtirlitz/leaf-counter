package leafCounter;

import java.util.ArrayList;
import java.util.List;

public class PostData {
	public List<Posts> posts = new ArrayList<Posts>();
	
	//public void setPostData(Posts newValue){
	//	posts = newValue;
	//}
	
	public String toString(){
		return posts.toString();
	}

}
