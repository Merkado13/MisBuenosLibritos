package mislibritos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class NewBookEmailData implements Serializable{

	public String authorName;
	public String title;
	public List<String> userEmails;
	
	public NewBookEmailData() {
		super();
	}
	
	public NewBookEmailData(String authorName, String title, List<String> userEmails) {
		this.authorName = authorName;
		this.title = title;
		this.userEmails = userEmails;
	}
	
}
