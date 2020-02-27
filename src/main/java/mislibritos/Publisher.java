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
	
	public Publisher(String name, String description, String email, String passwordHash, int year, String website, String... roles) {
		super(name, description, email, passwordHash, roles);
		this.name = name;
		this.year = year;
		this.website = website;
		
	}
	
//	public void AddPublishedBook(Book book) {
//		publishedBooks.addBook(book);
//	}
	
	public String getName() {
		return name;
	}
	
	public void setPublishedCollection(BookCollection bc) {
		publishedBooks = bc;
		
	}
	
	public BookCollection getPublishedBooks() {
		return publishedBooks;
	}
}
