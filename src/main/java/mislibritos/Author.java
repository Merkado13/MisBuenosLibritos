package mislibritos;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Author extends User{

	@OneToOne
	private BookCollection publishedBooks;
	
	private Date birth;
	private String country;
	private String website;
	
	public Author() {
		
	}
	public Author(String name, Date birth, String website) {
		super(name);
		this.birth = birth; 
		this.website = website;
	}
	
}
