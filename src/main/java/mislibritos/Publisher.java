package mislibritos;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Publisher extends User{

	@OneToOne
	private BookCollection publishedBooks; 
	
	private int year;
	private String website;
	
	public Publisher() {		
	}
	
	public Publisher(String name, String description, int year, String website) {
		super(name, description);
		this.name = name;
		this.year = year;
		this.website = website;
		
	}
	
	public void AddPublishedBook(Book book) {
		publishedBooks.addBook(book);
	}
}
