package mislibritos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BookCollection {
 
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String name;
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
