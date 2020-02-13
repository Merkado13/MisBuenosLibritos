package mislibritos;

import javax.persistence.Entity;

@Entity
public class Publisher extends User{

	
	private BookCollection publishedBooks; 
	
	private int year;
	private String website;
	
	public Publisher() {		
	}
	
	public Publisher(String name, int year, String website) {
		this.name = name;
		this.year = year;
		this.website = website;
		publishedBooks = new BookCollection();
	}
	
	public void AddPublishedBook(Book book) {
		publishedBooks.addBook(book);
	}
}
