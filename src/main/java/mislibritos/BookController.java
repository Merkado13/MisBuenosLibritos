package mislibritos;

import java.util.Arrays;
import java.util.List;

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
	
	@GetMapping("/books/{bookTitle}")
	public String books(Model model, HttpSession session, @PathVariable String bookTitle) {

		//Pillar el usuario a parti de la sesion
		User testUser = (User)session.getAttribute("user");
		Book book = bookRepository.findByTitle(bookTitle);
		
		if (book != null)
			model.addAttribute("book", book);
		else
			model.addAttribute("book", "undefined");
		
		model.addAttribute("added", false);
		
		String bookState = bookService.assertBookState(model, book, testUser);
		model.addAttribute("bookState", bookState);
		//model.addAttribute("collections", testUser.getBookCollection());
		model.addAttribute("collections", bookCollectionRepository.findByUser(testUser));
		return "books";

	}
	
	@PostMapping("/books/{bookTitle}")
	public String addBook(HttpSession session, Model model, @RequestParam String bookTitle, @RequestParam String collName) {
		
							
		//pillar el usuario de la sesión
		User testUser = (User)session.getAttribute("user");
		
		//pillar el libro de la base de datos
		Book book = bookRepository.findByTitle((bookTitle));
		model.addAttribute("book", book);
		model.addAttribute("added", true);
		
		//pillar la coleccion de la base de datos
		BookCollection bc = bookCollectionRepository.findByNameAndUser(collName, testUser);
		if(bc == null) {
			model.addAttribute("added", false);
			
		}else {
			bookService.insertBookIntoBookCollection(model, book, bc, testUser);			
		}
		
		
		String bookState = bookService.assertBookState(model, book, testUser);
		model.addAttribute("bookState", bookState);
		
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
