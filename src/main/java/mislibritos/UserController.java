package mislibritos;

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
	public String perfil(Model model, HttpSession session) {	
		User currentUser = (User)session.getAttribute("user");
		model.addAttribute("user", userRepository.findById(currentUser.getId()));
		//model.addAttribute("user", authorRepository.findByName("San Pablo"));
		//model.addAttribute("user", publisherRepository.findByName("HolyPublisher"));
		model.addAttribute("isAuthor", false);
		model.addAttribute("isPublisher", false);
		
		
		return "perfil";

	}	
	
	@GetMapping("/perfil/{name}")
	public String user(Model model, @PathVariable String name) {

		User user = userRepository.findByName(name);
		
		if (user instanceof Author) {
			model.addAttribute("user", user);
			model.addAttribute("isAuthor", true);
			model.addAttribute("isPublisher", false);			
		}

		else if (user instanceof Publisher) {
			model.addAttribute("user", user);
			model.addAttribute("isAuthor", false);	
			model.addAttribute("isPublisher", true);			
		}
		else {
			model.addAttribute("name", "undefined");
						
		}
		return "perfil";

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
