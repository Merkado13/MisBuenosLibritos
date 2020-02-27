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
	public Author(String name, String description,String email, String passwordHash,  Date birth, String country, String website, String... roles) {
		super(name, description, email, passwordHash, roles);
		this.birth = birth; 
		this.website = website;
		this.country = country;
	}

	
	public void setPublishedCollection(BookCollection bc) {
		publishedBooks = bc;
		
	}
	
	public BookCollection getPublishedBooks() {
		return publishedBooks;
	}
	
	public String getName() {
		return name;
	}
}
