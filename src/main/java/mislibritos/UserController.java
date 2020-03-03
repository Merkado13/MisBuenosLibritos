package mislibritos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private PublisherRepository publisherRepository;

	
	@RequestMapping("/perfil")
	public String perfil(Model model, HttpServletRequest request) {	
		String name = request.getUserPrincipal().getName();
		System.out.println(name);
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
