package mislibritos;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Author extends User{

	@OneToOne
	private BookCollection publishedBooks;
	
	private Date birth;
	private String country;
	private String website;
	
	@ManyToMany
	@ElementCollection(fetch = FetchType.EAGER)
	private List<User> subUsers;
	
	public Author() {
		
	}
	public Author(String name, String description,String email, String passwordHash,  Date birth, String country, String website, String... roles) {
		super(name, description, email, passwordHash, roles);
		this.birth = birth; 
		this.website = website;
		this.country = country;
		subUsers = new LinkedList<User>();
	}

	
	public void setPublishedCollection(BookCollection bc) {
		publishedBooks = bc;
		
	}
	
	public BookCollection getPublishedBooks() {
		return publishedBooks;
	}
	
	public List<User> getSubUsers() {
		return subUsers;
	}
	
	public String getName() {
		return name;
	}
}
