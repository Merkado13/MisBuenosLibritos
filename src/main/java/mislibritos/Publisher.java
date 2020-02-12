package mislibritos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Publisher extends User{

	
	@OneToMany(mappedBy="publisher")
	private List<Book> books;
	
	private int year;
	private String website;
	
	public Publisher() {
		
	}
	
	public Publisher(String name, int year, String website) {
		this.name = name;
		this.year = year;
		this.website = website;
		books = new ArrayList<Book>();
	}
	
	public void AddBook(Book book) {
		books.add(book);
	}
}
