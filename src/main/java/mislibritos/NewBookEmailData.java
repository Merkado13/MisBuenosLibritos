package mislibritos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;


public class NewBookEmailData implements Serializable{

	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	public String authorName;
	public String title;
	public List<String> userEmails;
	
	public NewBookEmailData() {
	
	}
	
	public NewBookEmailData(String authorName, String title, List<String> userEmails) {
		this.authorName = authorName;
		this.title = title;
		this.userEmails = userEmails;
	}
	
	public String getAuthorName() {
		return authorName;
		
	}
	
	public String getTitle() {
		return title;
	}
	
	public List<String> getUserEmails(){
		return userEmails;
	}
}
