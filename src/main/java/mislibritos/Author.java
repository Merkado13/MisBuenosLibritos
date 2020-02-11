package mislibritos;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Author extends User{

	@OneToOne
	private BookCollection publishedBooks;
	
	private Date birth;
	private String country;
	private String website;
	
	public Author() {
		
	}
	public Author(String name) {
		super(name);
	}
	
}
