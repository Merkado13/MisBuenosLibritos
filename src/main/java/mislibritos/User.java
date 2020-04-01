package mislibritos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long id; 
	protected String name; 
	protected String description;
	protected String email; 	
	protected String passwordHash;
	@ElementCollection(fetch = FetchType.EAGER)
	protected Map<String, Integer> Ratings;
	
	
	@OneToMany(mappedBy = "user")
	private List<BookCollection> bookCollection;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles;

	
	public User() {
		
	}

	public User(String name, String description, String email, String passwordHash, String... roles) {		
		this.name = name;
		this.description = description;
		this.email = email; 
		this.passwordHash = passwordHash; 
		this.roles = new ArrayList<>(Arrays.asList(roles));
		bookCollection = new LinkedList<BookCollection>();
		Ratings = new HashMap<String, Integer>();
	}
	
	public void AddCollection(BookCollection bc) {
		bookCollection.add(bc);
		
	}
	
	public void AddCollection(List<BookCollection> bcl) {
		bookCollection.addAll(bcl);
	}
	
	public List<BookCollection> getBookCollection() {
		return bookCollection;
	}
	
	public long getId() {
		return id;
		
	}
	
	public String getName() {
		return name; 
	}
	
	public String getPasswordHash() {
		return passwordHash; 
	}
	
	public List<String> getRoles(){
		return roles; 
	}
	
	public String getEmail() {
		return email;
	}
	
	
}
