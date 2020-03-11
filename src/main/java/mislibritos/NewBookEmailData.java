package mislibritos;

import java.io.Serializable;
import java.util.List;

public class NewBookEmailData implements Serializable{

	private String authorName;
	private String title;
	private List<String> userEmails;
	
	public NewBookEmailData(String authorName, String title, List<String> userEmails) {
		this.authorName = authorName;
		this.title = title;
		this.userEmails = userEmails;
	}
	
}
