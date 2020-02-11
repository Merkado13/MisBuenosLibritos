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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;


enum Genre{ACTION, ART, AUTOBIOGRAPHY, BIOGRAPHY, CHILDREN, COMIC, COOKBOOK, 
	CRIME, DRAMA, FANTASY, GRAPHIC_NOVEL, HEALTH, HISTORICAL_FICTION, HISTORY,
	HORROR, MEMOIR, MYSTERY, PEOTRY, POLITICAL_THRILLER, RELIGION, ROMANCE, 
	SCIENCE, SCIENCE_FICTION, SELF_HELP, SHORT_STORY, SUSPENSE, THRILLER, TRAVEL,
	TRUE_CRIME, YOUNG_ADULT
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
	
	//@ManyToOne
	//private List<Editorial> editorial;
	
		
	public Book() {}
	
	public Book(String title, double rating, int numRatings, String description, Genre genre, List<Genre> tags) {
		super();
		this.title = title;
		this.rating = rating;
		this.numRatings = numRatings;
		this.description = description;
		this.genre = genre;
		this.tags = tags;
	}
	
}
