package mislibritos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LibritosController {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private PublisherRepository publisherRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired BookCollectionRepository bookCollectionRepository;
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	@PostConstruct
	public void init() throws ParseException {
		
		String s = "Soy una descripción";
		
		List<Genre> tagsBiblia = Arrays.asList(Genre.ACTION,Genre.RELIGION);	
		List<Genre> tagsNecronomicon = Arrays.asList(Genre.AUTOBIOGRAPHY,Genre.RELIGION);		
		
		Author autor = userService.getNewAuthor("San Pablo", s, "sanpablo@gmail.com", passwordEncoder.encode("1234"),new SimpleDateFormat("dd/MM/yyyy").parse("05/05/0005"), "Turquía", "www.vivajesusito.com", "ROLE_USER", "ROLE_AUTHOR");
		
		
		List<Author> autoresBiblia = Arrays.asList(autor,
				userService.getNewAuthor("San Marcos", s, "sanmarcos@email.com", passwordEncoder.encode("1234"), new SimpleDateFormat("dd/MM/yyyy").parse("02/02/0002"), "Grecia", "www.vivajesusito.com", "ROLE_USER", "ROLE_AUTHOR"),
				userService.getNewAuthor("San Mateo",s, "sanmateo@email.com", passwordEncoder.encode("1234"), new SimpleDateFormat("dd/MM/yyyy").parse("07/07/0007"), "Israel", "www.vivajesusito.com", "ROLE_USER", "ROLE_AUTHOR"),
				userService.getNewAuthor("San Lucas", s, "sanlucas@email.com", passwordEncoder.encode("1234"),new SimpleDateFormat("dd/MM/yyyy").parse("12/12/0012"),"Turquía", "www.vivajesusito.com", "ROLE_USER", "ROLE_AUTHOR"));
		List<Author> autoresNecronomicon = Arrays.asList(
				userService.getNewAuthor("Jose", s, "jose@email.com", passwordEncoder.encode("1234"), new SimpleDateFormat("dd/MM/yyyy").parse("09/05/1883"), "Españita", "www.soyjose.com", "ROLE_USER", "ROLE_AUTHOR"),
				userService.getNewAuthor("Ortega",s, "ortega@email.com", passwordEncoder.encode("1234"),new SimpleDateFormat("dd/MM/yyyy").parse("09/05/1883"),"Españita",  "www.soyortega.com", "ROLE_USER", "ROLE_AUTHOR"),
				userService.getNewAuthor("Gasset",s, "gasset@email.com", passwordEncoder.encode("1234"),new SimpleDateFormat("dd/MM/yyyy").parse("09/05/1883"), "ESpañita", "www.juntosformamosjoseortegaygasset.com", "ROLE_USER", "ROLE_AUTHOR"));
			
		
		Publisher holyPublisher = userService.getNewPublisher("HolyPublisher",s, "holyemail@gmail.com", passwordEncoder.encode("1234"), 2010,"holy.god", "ROLE_USER", "ROLE_PUBLISHER");
		
		
		Book b1 = new Book("La Biblia", autoresBiblia, holyPublisher, Genre.ACTION, tagsBiblia, "Jesusito nace, se muere, vuelve a la vida, y siguen pasando cosas", 54, 11,1234567891012L);		
		bookRepository.save(b1);	
		for (Author a : autoresBiblia) {
			a.getPublishedBooks().addBook(b1);
			bookCollectionRepository.save(a.getPublishedBooks());
		}
		

		
		
		Book b2 = new Book("El Necronomicón", autoresNecronomicon, holyPublisher, Genre.AUTOBIOGRAPHY, tagsNecronomicon, "Ocurren cosas oscuras",19 , 6,9876543211012L);		
		bookRepository.save(b2);
		for (Author a : autoresNecronomicon) {
			a.getPublishedBooks().addBook(b2);
			bookCollectionRepository.save(a.getPublishedBooks());
		}

	}

	@RequestMapping("/home")
	public String home(HttpServletRequest request, Model model) {

		
			
		if(request.getUserPrincipal()!=null) {		
		
			String name = request.getUserPrincipal().getName();
			User currentUser = (User) userRepository.findByName(name);
			if(currentUser != null) {
				model.addAttribute("user", currentUser);
				
			}else {
				System.out.println("El usuario no está");
				model.addAttribute("user", "undefined");
				
			}
		}else {
			model.addAttribute("user", "undefined");
			
		}
		model.addAttribute("isRegistered", userService.isRegistered(request));
		model.addAttribute("all_books", bookRepository.findAll());

		return "home";
	}

	
	
	
	
	
	@RequestMapping("/busqueda")
	public String busqueda(HttpServletRequest request, Model model, @RequestParam String input) {
		
		model.addAttribute("books",bookRepository.findByTitleContaining(input));
		model.addAttribute("authors", authorRepository.findByNameContaining(input));
		model.addAttribute("publishers", publisherRepository.findByNameContaining(input));
		model.addAttribute("input", input);
		model.addAttribute("isRegistered", userService.isRegistered(request));
		return "busqueda";

	}

	
	 @GetMapping("/login")
	 public String login() {
		 return "login";
	 }
	 
	 @GetMapping("/loginerror")
	 public String loginError() {
		 return "loginerror";
	 }
	
}