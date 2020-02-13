package mislibritos;

import java.util.Collection;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;


enum Genre{ACTION, ART, AUTOBIOGRAPHY, BIOGRAPHY, CHILDREN, COMIC, COOKBOOK, 
	CRIME, DRAMA, FANTASY, GRAPHIC_NOVEL, HEALTH, HISTORICAL_FICTION, HISTORY,
	HORROR, LGTBI, MEMOIR, MYSTERY, NEW_ADULT, PEOTRY, POLITICAL_THRILLER, RELIGION, 
	ROMANCE, SCIENCE, SCIENCE_FICTION, SELF_HELP, SHORT_STORY, SUSPENSE, THRILLER,
	TRAVEL,	TRUE_CRIME, YOUNG_ADULT
	}

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long isbn;
	private String title;
	private double rating;
	private int numRatings; 
	private String description; 

	
	@ElementCollection //especifica que es una colección de elementos, va a crear una tabla adicional
	///////Estructura de la tabla
	@CollectionTable(name = "book_genres", joinColumns = @JoinColumn(name = "id")) 
	@Column(name = "genre", nullable = false)
	///////FIN Estructura de la tabla
	@Enumerated(EnumType.STRING)
	private List<Genre> tags; 
	
	@Enumerated(EnumType.STRING)
	private Genre genre;
	

	@ManyToMany
	private List<Author> authors;
	
	@ManyToOne
	private Publisher publisher;
	
		
	public Book() {}
	
	public Book(String title, List<Author> authors, Publisher publisher, Genre genre, List<Genre> tags, 
			String description, double rating, int numRatings) {
		this.title = title;
		this.authors = authors;
		this.publisher = publisher;
		this.genre = genre;
		this.tags = tags;
		this.description = description;
		this.rating = rating;
		this.numRatings = numRatings;
		
	}

	public long getIsbn() {
		return isbn;
	}

	public String getTitle() {
		return title;
	}

	public double getRating() {
		return rating;
	}

	public int getNumRatings() {
		return numRatings;
	}

	public String getDescription() {
		return description;
	}

	public List<Genre> getTags() {
		return tags;
	}

	public Genre getGenre() {
		return genre;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public Publisher getPublisher() {
		return publisher;
	}
	
	
	
}
