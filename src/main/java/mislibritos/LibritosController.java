
package mislibritos;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
	@Autowired
	private PublisherRepository publisherRepository;
	
	@PostConstruct
	public void init() throws ParseException {
		
		String s = "Soy una descripción";
		
		List<Genre> tagsBiblia = Arrays.asList(Genre.ACTION,Genre.RELIGION);	
		List<Genre> tagsNecronomicon = Arrays.asList(Genre.AUTOBIOGRAPHY,Genre.RELIGION);		
		
		
		List<Author> autoresBiblia = Arrays.asList(new Author("San Pablo", s, new SimpleDateFormat("dd/MM/yyyy").parse("05/05/0005"), "Turquía", "www.vivajesusito.com"),
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
		
		
		Book b1 = new Book("La Biblia", autoresBiblia, holyPublisher, Genre.ACTION, tagsBiblia, "Jesusito nace, se muere, vuelve a la vida, y siguen pasando cosas", 3, 30);		
		bookRepository.save(b1);	
		Book b2 = new Book("El Necronomicón", autoresNecronomicon, holyPublisher, Genre.AUTOBIOGRAPHY, tagsNecronomicon, "Ocurren cosas oscuras", 4.5, 45);		
		bookRepository.save(b2);
		
		BookCollection librosSagrados = new BookCollection("Libros Sagrados", "Los mejores libros que podrás encontrar");
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