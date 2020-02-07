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
	private long Id; 
	private String name; 
	
	@OneToMany
	private List<BookCollection> bookCollection;
	
	public User() {
		
	}


	public User(String name) {		
		this.name = name;
		bookCollection = new LinkedList<BookCollection>();
	}
	
	public void AddCollection(BookCollection bc) {
		bookCollection.add(bc);
		
	}

}
