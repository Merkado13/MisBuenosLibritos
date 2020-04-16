package mislibritos;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.codehaus.jackson.annotate.JsonIgnore;





enum Genre{ACTION, ART, AUTOBIOGRAPHY, BIOGRAPHY, CHILDREN, COMIC, COOKBOOK, 
	CRIME, DRAMA, FANTASY, GRAPHIC_NOVEL, HEALTH, HISTORICAL_FICTION, HISTORY,
	HORROR, LGTBI, MEMOIR, MYSTERY, NEW_ADULT, PEOTRY, POLITICAL_THRILLER, RELIGION, 
	ROMANCE, SCIENCE, SCIENCE_FICTION, SELF_HELP, SHORT_STORY, SUSPENSE, THRILLER,
	TRAVEL,	TRUE_CRIME, YOUNG_ADULT
	}

//enum Genre{ACCIÓN, ARTE, AUTO_AYUDA, AUTOBIOGRAFÍA, BIOGRAFÍA, CIENCIA, CIENCIA_FICCIÓN, 
//	COCINA, COMIC, CRIMEN, CRIMEN_REAL, DRAMA, ERÓTICA, FANTASÍA, FICCIÓN_HISTÓRICA, 
//	HISTORIA, HORROR, JOVEN_ADULTO, LGTBI, MEMORIA, MISTERIO,  NIÑOS, NOVELA_GRÁFICA, 
//	NUEVO_ADULTO, POESÍA, RELATOS_CORTOS, RELIGIÓN, ROMANCE, SALUD,  SUSPENSE,
//	THRILLER, THRILLERS_POLÍTICOS, VIAJE
//	}

enum BookState{PARA_LEER, LEYENDO,LEÍDO,NONE}

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private long isbn;
	private String title;
	private double rating;
	private double sumRatings;
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
	

	@ManyToMany(mappedBy="publishedBooks")
	@JsonIgnore
	private List<Author> authors;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Publisher publisher;
	
		
	public Book() {}
	
	public Book(String title, List<Author> authors, Publisher publisher, Genre genre, List<Genre> tags, 
			String description, double sumRatings, int numRatings, long isbn) {
		this.title = title;
		this.authors = authors;
		this.publisher = publisher;
		this.genre = genre;
		this.tags = tags;
		this.description = description;
		this.sumRatings = sumRatings;
		this.rating = numRatings == 0 ? 0 : sumRatings/numRatings;
		this.numRatings = numRatings;
		this.isbn = isbn;
		
	}

	public long getId() {
		return id;
	}
	public void addNewRating(double r) {
		sumRatings+=r;
		numRatings+=1;
		rating = sumRatings/numRatings;
	}
	public void updateRating(double old, double newR) {
		sumRatings-=old;
		sumRatings+=newR;
		rating = sumRatings/numRatings;
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

	public double getSumRatings() {
		return sumRatings;
	}

	public void setSumRatings(double sumRatings) {
		this.sumRatings = sumRatings;
	}

	public void setNumRatings(int numRatings) {
		this.numRatings = numRatings;
	}
	
	@Override
	public boolean equals(Object o) {
		Book b = (Book) o;
		if(b.title.equals(this.title)){
			return true;
		}
		return false;
	}
	
	
}
