package mislibritos;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private PublisherRepository publisherRepository;
	@Autowired
	private UserService us;

	
	@RequestMapping("/perfil")
	public String perfil(Model model, HttpServletRequest request) {	
		String name = request.getUserPrincipal().getName();
		
		User currentUser = (User) userRepository.findByName(name);
			if(currentUser != null) {
				model.addAttribute("user", userRepository.findById(currentUser.getId()));
				//model.addAttribute("user", authorRepository.findByName("San Pablo"));				
				//model.addAttribute("user", publisherRepository.findByName("HolyPublisher"));
				model.addAttribute("isAuthor", request.isUserInRole("ROLE_AUTHOR"));
				model.addAttribute("isPublisher", request.isUserInRole("ROLE_PUBLISHER"));		
				
				return "perfil";
			}
		return "home";
	}	
	
	@RequestMapping("/crearusuario")
	public String crearUsuario(Model model) {	
		model.addAttribute("ok", false);
		model.addAttribute("message", "Crear usuario");	
		
		return "crearusuario";

	}	

	@PostMapping("/crearusuario")
	public String addedBook(Model model, @RequestParam String username, @RequestParam String description, 
			@RequestParam String email, @RequestParam String password, @RequestParam String rol,
			@RequestParam String birth, @RequestParam String country, @RequestParam String website) throws ParseException {		
	
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		model.addAttribute("ok", false);
		
		User user = userRepository.findByName(username);
		User user2 = userRepository.findByEmail(email);
		User u = new User();
		if(user != null) {
			model.addAttribute("message", "Ya existe un usuario con ese nombre");		
		}
		else if(user2 != null) {
			model.addAttribute("message", "Ya existe un usuario con ese email");		
		}else {
			if(rol.equals("User")) {
				u = us.getNewUser(username, description, email, passwordEncoder.encode(password), "ROLE_USER");		
				
			}else if(rol.equals("Author")) {
				u = us.getNewAuthor(username, description, email, passwordEncoder.encode(password), new SimpleDateFormat("dd/MM/yyyy").parse(birth), country, website, "ROLE_AUTHOR");
				
			}else {			
				u = us.getNewPublisher(username, description, email, passwordEncoder.encode(password), Integer.valueOf(birth), website, "ROLE_PUBLISHER");
			}
			
			if(u!=null) {
				model.addAttribute("message", "El usuario se ha creado correctamente");	
				
			}else {
				model.addAttribute("message", "Ha ocurrido un problema. Inténtalo de nuevo");	
			}
			
		}		
		
		model.addAttribute("ok", true);		
		return "crearusuario";
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

}
