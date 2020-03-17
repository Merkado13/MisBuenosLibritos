package mislibritos;

import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class BookController {
	
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private BookCollectionRepository bookCollectionRepository;
	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private PublisherRepository publisherRepository;	
	@Autowired
	private BookService bookService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmailService emailService;
	DecimalFormat numberFormat = new DecimalFormat("#.00");
	
	@GetMapping("/books/{bookTitle}")
	public String books(Model model, HttpServletRequest request, @PathVariable String bookTitle) {
		
		model.addAttribute("isRegistered", userService.isRegistered(request));	
		Book book = bookRepository.findByTitle(bookTitle);
		if (book != null) {
			model.addAttribute("book", book);

			model.addAttribute("rating", numberFormat.format(book.getRating()));
			}
		else
			model.addAttribute("book", "undefined");
			
		
		model.addAttribute("added", false);
		
		if(request.getUserPrincipal()!=null) {
			String name = request.getUserPrincipal().getName();			
			User currentUser = (User) userRepository.findByName(name);
			if(currentUser != null) {
				model.addAttribute("user", userRepository.findById(currentUser.getId()));			
				String bookState = bookService.assertBookState(model, book, currentUser);
				model.addAttribute("bookState", bookState);
				//model.addAttribute("collections", testUser.getBookCollection());
				System.out.println(bookState+ " en el controller GET");
				model.addAttribute("collections", bookCollectionRepository.findByUser(currentUser));
			}
		}else {
		model.addAttribute("bookState", BookState.NONE);
		
		}
		return "books";

	}
	
	@PostMapping("/books/{bookTitle}")
	public String addBook(HttpServletRequest request, Model model, @RequestParam String peticion, @RequestParam String bookTitle, @RequestParam String datos) {
		model.addAttribute("isRegistered", userService.isRegistered(request));					
		//System.out.println("POST. "+peticion);
		if(request.getUserPrincipal()!=null){
			
				String name = request.getUserPrincipal().getName();			
				User currentUser = (User) userRepository.findByName(name);
				Book book = bookRepository.findByTitle((bookTitle));
				
				
				if(currentUser != null) {
					
					
					if(peticion.equals("col")) {	
						//pillar el libro de la base de datos
						
						
						model.addAttribute("added", true);
	
				
						//pillar la coleccion de la base de datos
						BookCollection bc = bookCollectionRepository.findByNameAndUser(datos, currentUser);
						
						if(bc == null) {
							model.addAttribute("added", false);
							
						}else {
							bookService.insertBookIntoBookCollection(model, book, bc, currentUser);			
						}
					}else if(peticion.equals("rate")) {
						if(currentUser.Ratings.get(book.getTitle()) != null) {
							// ya había votado
							double oldRating = currentUser.Ratings.get(book.getTitle());
							book.updateRating(oldRating, Double.valueOf(datos));
							currentUser.Ratings.put(book.getTitle(), Integer.valueOf(datos));
							bookRepository.save(book);
							
							//System.out.println("Cambiando puntuación. Nueva: "+ datos);
						}else {
							// vota por primera vez
							book.addNewRating(Double.valueOf(datos));
							currentUser.Ratings.put(book.getTitle(), Integer.valueOf(datos));
							bookRepository.save(book);
							
							//System.out.println("Añadiendo puntuacion: "+ datos);
						}
						
							
					}
					
					book = bookRepository.findByTitle((bookTitle));
					model.addAttribute("book", book);
					model.addAttribute("user", userRepository.findById(currentUser.getId()));			
					String bookState = bookService.assertBookState(model, book, currentUser);
					model.addAttribute("bookState", bookState);
					System.out.println(bookState+ " en el controller GET");
					model.addAttribute("collections", bookCollectionRepository.findByUser(currentUser));
					model.addAttribute("rating", numberFormat.format(book.getRating()));
					
				}
				
			}else {
				model.addAttribute("bookState", BookState.NONE);
			}
		
		
		
		return "books";

		
		
	}
	
	@GetMapping("/addBook")
	public String addBook(Model model) {
		
		model.addAttribute("genres", Genre.values());
		return "nuevolibro";
	}
	@PostMapping("/addBook")
	public String addedBook(Model model, @RequestParam String title, @RequestParam String description, 
			@RequestParam String author, @RequestParam String publisher, @RequestParam String isbn,
			@RequestParam Genre genre, @RequestParam List<Genre> tags) throws RestClientException, JsonProcessingException, URISyntaxException {		
		
		Author a = authorRepository.findByName(author);		
		Publisher p = publisherRepository.findByName(publisher);
		
		model.addAttribute("isbnIncorrect", false);
		model.addAttribute("bookTitleExists", false);
		model.addAttribute("isbnExists", false);
		model.addAttribute("ok", false);
		
		model.addAttribute("genres", Genre.values());	
		
		if(a == null || p == null || isbn.length() != 13) {						
			if(isbn.length()!=13) {
				model.addAttribute("isbnIncorrect", true);
			}			
			return "nuevolibro";
		}		
		Book auxB = bookRepository.findByTitle(title);
		if(auxB!=null) {		
			model.addAttribute("bookTitleExists", true);
			return "nuevolibro";
		}
		auxB = bookRepository.findByIsbn(Long.parseLong(isbn));
		if(auxB!=null) {		
			model.addAttribute("isbnExists", true);		
			return "nuevolibro";
		}		
		
		Book b = new Book(title, Arrays.asList(a), p, genre, tags, description, 0, 0, Long.parseLong(isbn));
		bookRepository.save(b);		
		
		a.getPublishedBooks().addBook(b);
		bookCollectionRepository.save(a.getPublishedBooks());	
		
		p.getPublishedBooks().addBook(b);
		bookCollectionRepository.save(p.getPublishedBooks());
		
		//Enviar correo a todos los usuarios
		emailService.sendNewBookEmail(author, title);
		//
		
		model.addAttribute("ok", true);		
		return "nuevolibro";
	}


}
