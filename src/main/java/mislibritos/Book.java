package mislibritos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long isbn;
	private String name;
	
	public Book() {}
	
	public Book(String name) {
		this.name = name;
	}
	
}
