package mislibritos;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long id; 
	protected String name; 
	protected String description;
	
	@OneToMany
	private List<BookCollection> bookCollection;
	
	public User() {
		
	}


	public User(String name, String description) {		
		this.name = name;
		this.description = description;
		bookCollection = new LinkedList<BookCollection>();
	}
	
	public void AddCollection(BookCollection bc) {
		bookCollection.add(bc);
		
	}
	
	public List<BookCollection> getBookCollection() {
		return bookCollection;
	}
	
}
