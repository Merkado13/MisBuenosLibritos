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
	
	@ManyToMany
	private List<Book> books;
	
	public BookCollection() {
		// TODO Auto-generated constructor stub
	}

	public BookCollection(String name) {
		this.name = name;
		books = new LinkedList<Book>();
	}
	
	public void AddBook(Book b) {
		books.add(b);
	}
	
}
