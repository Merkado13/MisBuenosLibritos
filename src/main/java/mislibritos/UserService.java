package mislibritos;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserRepository authorRepository;
	@Autowired
	private UserRepository publisherRepository;
	@Autowired
	private BookCollectionRepository bookCollectionRepository;

	/*Crea un nuevo usuario con las listas por defecto correspodientes y lo guarda*/
	public User getNewUser(String name, String description) {
		
		User user = new User(name, description);
		user.AddCollection(prepareDefaultCollections(user));
		userRepository.save(user);
		return user;		
	}
	
	/*Crea un nuevo autor con las listas por defecto correspodientes y lo guarda*/
	public Author getNewAuthor(String name, String description, Date birth, String country, String website) {
		
		Author author = new Author(name, description, birth, country, website);
		author.AddCollection(prepareDefaultCollections(author));
		authorRepository.save(author);
		return author;
	}
	
	/*Crea ua nueva editorial con las listas por defecto correspodientes y lo guarda*/
	public Publisher getNewPublisher(String name, String description, int year, String website) {
		
		Publisher publisher = new Publisher(name, description,year,website);
		publisher.AddCollection(prepareDefaultCollections(publisher));
		publisherRepository.save(publisher);
		return publisher;
	}
	
	/*
	 * Crea las listas por defecto de: TO_READ, READING y READ
	 * Adicionalmente si es un autor o una editorial crea la lista por defecto PUBLISHED
	 * Guarda las coleciones es su repositorio y devuelve la lista de colecciones
	 * 
	 * */
	private List<BookCollection> prepareDefaultCollections(User user){
		
		List<BookCollection> defaultColl = new LinkedList<BookCollection>(); 
		
		defaultColl.add(new BookCollection("To Read", "Books you want to read", BookCollection.DEFAULT));
		defaultColl.add(new BookCollection("Reading", "Books you are currently reading", BookCollection.DEFAULT));
		defaultColl.add(new BookCollection("Read", "Books you have read", BookCollection.DEFAULT));

		//Se podria mejorar para no mirar que clases son, pero por ahora hace el trabajo
		if(user.getClass() == Author.class) {
			Author au = (Author) user;
			BookCollection publishedBooks = new BookCollection("Published Books de " + au.getName(), "Los libritos que he publicado ", BookCollection.DEFAULT);
			au.setPublishedCollection(publishedBooks);
			bookCollectionRepository.save(publishedBooks);
		}
		
		if(user.getClass() == Publisher.class) {
			Publisher pu = (Publisher) user;
			BookCollection publishedBooks = new BookCollection("Published Books por " + pu.getName(), "Los libritos que he publicado ", BookCollection.DEFAULT);
			pu.setPublishedCollection(publishedBooks);
			bookCollectionRepository.save(publishedBooks);
		}
		
		for(BookCollection col : defaultColl) {
			bookCollectionRepository.save(col);
		}
		
		return defaultColl;
	}
}
