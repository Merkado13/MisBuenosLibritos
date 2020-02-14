package mislibritos;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class BookCollection {
 
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String name;
	private String description;
	
	@ManyToMany
	private List<Book> books;
	
	public BookCollection() {
		// TODO Auto-generated constructor stub
	}

	public BookCollection(String name, String description) {
		this.name = name;
		books = new LinkedList<Book>();
		this.description = description;
	}
	
	public void addBook(Book b) {
		books.add(b);
	}
	public long getId() {
		return id;
	}
	
}
