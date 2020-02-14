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
	public Author(String name, String description, Date birth, String country, String website) {
		super(name, description);
		this.birth = birth; 
		this.website = website;
		this.country = country;
	}
	
	public void addPublishedBook(Book book) {
		publishedBooks.addBook(book);
		
	}
	
	
}
