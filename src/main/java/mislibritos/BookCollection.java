package mislibritos;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.Transient;

@Entity
public class BookCollection {
 
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String name;
	private String description;
	private boolean custom;
	
	@Transient
	public final static boolean DEFAULT = false;
	@Transient
	public final static boolean CUSTOM = true;
	
	@Transient
	public final static String TO_READ_COLLECTION_NAME = "Por leer";
	@Transient
	public final static String READING_COLLECTION_NAME = "Leyendo";
	@Transient
	public final static String READ_COLLECTION_NAME = "Leído";
	
	@ManyToMany
	private List<Book> books;
	
	@ManyToOne
	private User user;
	
	public BookCollection() {
		// TODO Auto-generated constructor stub
	}

	public BookCollection(String name, String description, boolean custom) {
		this.name = name;
		books = new LinkedList<Book>();
		this.custom = custom;
		this.description = description;
	}
	
	public boolean containsBooks(Book b) {
		return books.contains(b);
	}
	
	public void addBook(Book b) {
		books.add(b);
	}
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
}
