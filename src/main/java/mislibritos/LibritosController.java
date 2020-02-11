package mislibritos;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LibritosController{
	
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private BookCollectionRepository bookCollectionRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthorRepository authorRepository;
	
	@PostConstruct
	public void init() {
//		
//		List<genres> tags = new ArrayList<genres>();
//		
//		tags.add(genres.DRAMA);
//		tags.add(genres.BIOGRAPHY);
//		tags.add(genres.RELIGION); 
//		tags.add(genres.HISTORICAL_FICTION);
		
		Book b1 = new Book("La Biblia", 3, 30, "Jesusito nace, se muere, vuelve a la vida, y siguen pasando cosas", genres.RELIGION);		
		bookRepository.save(b1);	
		b1 = new Book("El Necronomicón", 4.5, 20, "Ocurren cosas oscuras", genres.RELIGION);
		bookRepository.save(b1);
		
		BookCollection librosSagrados = new BookCollection("Libros Sagrados", "Los mejores libros que podrás encontrar");
		librosSagrados.AddBook(bookRepository.findByTitle("La Biblia"));
		librosSagrados.AddBook(bookRepository.findByTitle("El Necronomicón"));
		BookCollection librosPrueba = new BookCollection("Libros Prueba", "Mis libritos de prueba");
		librosPrueba.AddBook(bookRepository.findByTitle("La Biblia"));
		
		bookCollectionRepository.save(librosSagrados);
		bookCollectionRepository.save(librosPrueba);
		
		User user1 = new User("God");
		user1.AddCollection(librosSagrados);
		userRepository.save(user1);
		
		Author autor1 = new Author("Escritorcito");
		authorRepository.save(autor1);
	}
	
	@RequestMapping("/home")
	public String home(Model model) {
		
		
		model.addAttribute("bookitos", bookCollectionRepository.findAll());
		
		return "home";
	}
	@RequestMapping("/colecciones")
	public String colecciones(Model model) {
		//model.addAttribute("pagina_anterior", anterior);
		model.addAttribute("user", userRepository.findAll());
		return "colecciones";
		
	}
	@RequestMapping("/busqueda")
	public String busqueda(Model model, @RequestParam String input) {
		model.addAttribute("input", input);
		return "busqueda";
		
	}
	@RequestMapping("/perfil")
	public String perfil(Model model) {
		
		model.addAttribute("user", userRepository.findAll());
		
		return "perfil";
		
	}
	

}