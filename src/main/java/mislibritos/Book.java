package mislibritos;

import java.util.Collection;
import java.util.List;

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


enum genres{ACTION, ART, AUTOBIOGRAPHY, BIOGRAPHY, CHILDREN, COMIC, COOKBOOK, 
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
	//@Enumerated(EnumType.STRING)
	//private List<genres> tags; 
	@Enumerated(EnumType.STRING)
	private genres genre;
	

	@ManyToMany
	private List<Author> authors;
	
	//@ManyToOne
	//private List<Editorial> editorial;
	
		
	public Book() {}
	
	public Book(String title, double rating, int numRatings, String description, genres genre) {
		super();
		this.title = title;
		this.rating = rating;
		this.numRatings = numRatings;
		this.description = description;
		this.genre = genre;
	}
	
}
