package mislibritos;

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
	private UserRepository userRepository;
	
	
	@GetMapping("/books/{bookTitle}")
	public String books(Model model, HttpServletRequest request, @PathVariable String bookTitle) {

		model.addAttribute("registered", false);
		Book book = bookRepository.findByTitle(bookTitle);
		
		if (book != null)
			model.addAttribute("book", book);
		else
			model.addAttribute("book", "undefined");
		
		model.addAttribute("added", false);
		
		if(request.getUserPrincipal()!=null) {
			String name = request.getUserPrincipal().getName();			
			User currentUser = (User) userRepository.findByName(name);
			if(currentUser != null) {
				model.addAttribute("user", userRepository.findById(currentUser.getId()));			
				
				model.addAttribute("registered", true);
				String bookState = bookService.assertBookState(model, book, currentUser);
				model.addAttribute("bookState", bookState);
				//model.addAttribute("collections", testUser.getBookCollection());
				System.out.println(bookState+ "en el controller GET");
				model.addAttribute("collections", bookCollectionRepository.findByUser(currentUser));
			}
		}else {
		model.addAttribute("bookState", BookState.NONE);
		
		}
		return "books";

	}
	
	@PostMapping("/books/{bookTitle}")
	public String addBook(HttpServletRequest request, Model model, @RequestParam String bookTitle, @RequestParam String collName) {
		
		model.addAttribute("registered", false);	
		
		if(request.getUserPrincipal()!=null){
			String name = request.getUserPrincipal().getName();			
			User currentUser = (User) userRepository.findByName(name);
			if(currentUser != null) {
				model.addAttribute("registered", true);
				//pillar el libro de la base de datos
				Book book = bookRepository.findByTitle((bookTitle));
				model.addAttribute("book", book);
				model.addAttribute("added", true);
				
				//pillar la coleccion de la base de datos
				BookCollection bc = bookCollectionRepository.findByNameAndUser(collName, currentUser);
				if(bc == null) {
					model.addAttribute("added", false);
					
				}else {
					bookService.insertBookIntoBookCollection(model, book, bc, currentUser);			
				}
				
				
				String bookState = bookService.assertBookState(model, book, currentUser);
				System.out.println(bookState+ "en el controller POST");
				model.addAttribute("bookState", bookState);
			}
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
			@RequestParam Genre genre, @RequestParam List<Genre> tags) {		
		
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
		
		Book b = new Book(title, Arrays.asList(a), p, genre, tags, description, 0.0,0, Long.parseLong(isbn));
		bookRepository.save(b);		
		
		a.getPublishedBooks().addBook(b);
		bookCollectionRepository.save(a.getPublishedBooks());	
		
		p.getPublishedBooks().addBook(b);
		bookCollectionRepository.save(p.getPublishedBooks());
		
		model.addAttribute("ok", true);		
		return "nuevolibro";
	}


}
