package mislibritos;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LibritosController {

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private BookCollectionRepository bookCollectionRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private PublisherRepository publisherRepository;
	
	Book b1;
	
	
	@PostConstruct
	public void init() throws ParseException {

		String s = "Soy una descripción";
		
		List<Genre> tagsBiblia = Arrays.asList(Genre.ACTION,Genre.RELIGION);	
		List<Genre> tagsNecronomicon = Arrays.asList(Genre.AUTOBIOGRAPHY,Genre.RELIGION);		
		
		Author autor = new Author("San Pablo", s, new SimpleDateFormat("dd/MM/yyyy").parse("05/05/0005"), "Turquía", "www.vivajesusito.com");
		
		
		List<Author> autoresBiblia = Arrays.asList(autor,
				new Author("San Marcos", s, new SimpleDateFormat("dd/MM/yyyy").parse("02/02/0002"), "Grecia", "www.vivajesusito.com"),
				new Author("San Mateo",s,  new SimpleDateFormat("dd/MM/yyyy").parse("07/07/0007"), "Israel", "www.vivajesusito.com"),
				new Author("San Lucas", s, new SimpleDateFormat("dd/MM/yyyy").parse("12/12/0012"),"Turquía", "www.vivajesusito.com"));
		List<Author> autoresNecronomicon = Arrays.asList(new Author("Jose", s, new SimpleDateFormat("dd/MM/yyyy").parse("09/05/1883"), "Españita", "www.soyjose.com"),
				new Author("Ortega",s, new SimpleDateFormat("dd/MM/yyyy").parse("09/05/1883"),"Españita",  "www.soyortega.com"),
				new Author("Gasset",s, new SimpleDateFormat("dd/MM/yyyy").parse("09/05/1883"), "ESpañita", "www.juntosformamosjoseortegaygasset.com"));
		
		List<Author> allAuthors = new ArrayList(autoresBiblia);
		allAuthors.addAll(autoresNecronomicon);
		
		
		for(Author a : allAuthors){
			authorRepository.save(a);
		}		
		
		Publisher holyPublisher = new Publisher("HolyPublisher",s, 0,"holy.god");
		publisherRepository.save(holyPublisher);
		
		
		b1 = new Book("La Biblia", autoresBiblia, holyPublisher, Genre.ACTION, tagsBiblia, "Jesusito nace, se muere, vuelve a la vida, y siguen pasando cosas", 3, 30);		
		bookRepository.save(b1);	
		Book b2 = new Book("El Necronomicón", autoresNecronomicon, holyPublisher, Genre.AUTOBIOGRAPHY, tagsNecronomicon, "Ocurren cosas oscuras", 4.5, 45);		
		bookRepository.save(b2);

		BookCollection librosSagrados = new BookCollection("Libros Sagrados",
				"Los mejores libros que podrás encontrar");
		librosSagrados.addBook(bookRepository.findByTitle("La Biblia"));
		librosSagrados.addBook(bookRepository.findByTitle("El Necronomicón"));
		BookCollection librosPrueba = new BookCollection("Libros Prueba", "Mis libritos de prueba");
		librosPrueba.addBook(bookRepository.findByTitle("La Biblia"));

		bookCollectionRepository.save(librosSagrados);
		bookCollectionRepository.save(librosPrueba);

		User user1 = new User("God", s);
		user1.AddCollection(librosSagrados);
		userRepository.save(user1);
	}

	@RequestMapping("/home")
	public String home(HttpSession session, Model model) {

		if (session.isNew()) {
			BookCollection wantToReadCollection = new BookCollection("To Read", "Books you want to read");
			BookCollection readingCollection = new BookCollection("Reading", "Books you are currently reading");
			BookCollection readCollection = new BookCollection("Read", "Books you have read");

			bookCollectionRepository.save(wantToReadCollection);
			bookCollectionRepository.save(readingCollection);
			bookCollectionRepository.save(readCollection);
			
			User testUser = new User(session.getId(),"Usuario de testeo: " + session.getId());
			testUser.AddCollection(wantToReadCollection);
			testUser.AddCollection(readingCollection);
			testUser.AddCollection(readCollection);
			userRepository.save(testUser);
			
			session.setAttribute("user", testUser);
			
		}
		
		//mostrar las listas del test user
		List<BookCollection> userCollections = ((User)session.getAttribute("user")).getBookCollection();
		
		model.addAttribute("collections",userCollections);
		
		model.addAttribute("all_books", bookRepository.findAll());

		return "home";
	}

	@GetMapping("/colecciones")
	public String colecciones(Model model) {
		
		model.addAttribute("user", userRepository.findAll());
		return "colecciones";

	}
	@PostMapping("/newCollection")
	public String addCollection(Model model, HttpSession session, @RequestParam String name, @RequestParam String description) {
		BookCollection bc = new BookCollection(name, description);
		bookCollectionRepository.save(bc);
		User testUser = (User)session.getAttribute("user");
		testUser.AddCollection(bc);
		userRepository.insertBookCollectionToUser(testUser.id, bc.getId());
		model.addAttribute("user", userRepository.findAll());
		return "colecciones";

	}

	
	@GetMapping("/perfil/{name}")
	public String user(Model model, @PathVariable String name) {

		User user = userRepository.findByName(name);
		
		if (user instanceof Author) {
			model.addAttribute("user", user);
			model.addAttribute("isAuthor", true);
			model.addAttribute("isPublisher", false);
			System.out.println("Está autor");
		}

		else if (user instanceof Publisher) {
			model.addAttribute("user", user);
			model.addAttribute("isAuthor", false);	
			model.addAttribute("isPublisher", true);
			System.out.println("Está publisher");
		}
		else {
			model.addAttribute("name", "undefined");
						
		}
		return "perfil";

	}
	
	
	@GetMapping("/books/{bookTitle}")
	public String books(Model model, HttpSession session, @PathVariable String bookTitle) {

		Book book = bookRepository.findByTitle(bookTitle);

		if (book != null)
			model.addAttribute("book", book);
		else
			model.addAttribute("book", "undefined");
		
		model.addAttribute("added", false);
		User testUser = (User)session.getAttribute("user");
		model.addAttribute("collections", testUser.getBookCollection());
		return "books";

	}
	
	@GetMapping("/usuario/{name}")
	public String autor(Model model, @PathVariable String name) {

		Author author = authorRepository.findByName(name);

		if (author != null) {
			model.addAttribute("user", author);
			model.addAttribute("isAuthor", true);
		}else {
			Publisher publisher = publisherRepository.findByName(name);
			if (publisher != null) {
				model.addAttribute("user", publisher);
				
				
			}
			else {
				model.addAttribute("user", "undefined");
				
			}
			model.addAttribute("isAuthor", false);
		
		}
		return "usuario";

	}
	
	

	@PostMapping("/books/{bookTitle}")
	public String addBook(HttpSession session, Model model, @RequestParam String bookTitle, @RequestParam String collName) {
		//model.addAttribute("bookTitle", bookTitle + " AGREGADO");
		
		//pillar la sesion, el usuario y meterle el libro en una lista
		Book book = bookRepository.findByTitle((bookTitle));
		model.addAttribute("book", book);
		model.addAttribute("added", true);
		
		//meter libro en la lista de "want to read"
		User testUser = (User)session.getAttribute("user");
		BookCollection bc = bookCollectionRepository.findByName(collName);
		
		//bookCollectionRepository.save(testUser.getBookCollection().get(0));
		bookCollectionRepository.insertBookToCollection(bc.getId(), book.getIsbn());
		return "books";
	}

	@RequestMapping("/busqueda")
	public String busqueda(Model model, @RequestParam String input) {
		
		
		model.addAttribute("input", input);
		return "busqueda";

	}

	@RequestMapping("/perfil")
	public String perfil(Model model) {	
		
		model.addAttribute("user", userRepository.findByName("God"));
		//model.addAttribute("user", authorRepository.findByName("San Pablo"));
		//model.addAttribute("user", publisherRepository.findByName("HolyPublisher"));
		model.addAttribute("isAuthor", false);
		model.addAttribute("isPublisher", false);
		
		
		return "perfil";

	}
	@GetMapping("/addBook")
	public String addBook(Model model) {
		
		model.addAttribute("genres", Genre.values());
		return "nuevolibro";
	}
	@PostMapping("/addBook")
	public String addedBook(Model model, @RequestParam String title, @RequestParam String description, 
			@RequestParam String author, @RequestParam String publisher, @RequestParam Genre genre, @RequestParam List<Genre> tags) {
		
		Author a = authorRepository.findByName(author);
		
		Publisher p = publisherRepository.findByName(publisher);
		if(a == null || p == null) {
			model.addAttribute("ok", false);
			model.addAttribute("genres", Genre.values());
			return "nuevolibro";
		}
		
		Book b = new Book(title, Arrays.asList(a), p, genre, tags, description, 0.0,0);
		bookRepository.save(b);
		// meter libro en lista de libros escritos por el autor
		// meter libro en lista de libros publicados por la editorial
		model.addAttribute("ok", true);
		model.addAttribute("genres", Genre.values());
		return "nuevolibro";
	}

}