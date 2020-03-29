package mislibritos;

import javax.servlet.http.HttpServletRequest;
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
public class BookCollectionController {

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private BookCollectionRepository bookCollectionRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;
	@GetMapping("/colecciones")
	public String colecciones(Model model, HttpServletRequest request) {
		/*User testUser = (User)session.getAttribute("user");
		
		model.addAttribute("user", userRepository.findById(testUser.getId()));*/
		model.addAttribute("isRegistered", userService.isRegistered(request));	
		String name = request.getUserPrincipal().getName();
		
		User currentUser = (User) userRepository.findByName(name);
			if(currentUser != null) {
				model.addAttribute("user", currentUser);
				
			}else {
				model.addAttribute("user", "undefined");
				
			}
		return "colecciones";

	}
	@PostMapping("/colecciones")
	public String colecciones(Model model, HttpServletRequest request, @RequestParam String colId) {
		//User testUser = (User)session.getAttribute("user");
		model.addAttribute("isRegistered", userService.isRegistered(request));	
		bookCollectionRepository.deleteById(Long.parseLong(colId));
		
		String name = request.getUserPrincipal().getName();
		
		User currentUser = (User) userRepository.findByName(name);
			if(currentUser != null) {
				model.addAttribute("user", currentUser);
				
			}else {
				model.addAttribute("user", "undefined");
				
			}

		return "colecciones";

	}
	
	@PostMapping("/newCollection")
	public String addCollection(Model model, HttpServletRequest request, @RequestParam String name, @RequestParam String description) {
		
		String n = request.getUserPrincipal().getName();
		model.addAttribute("isRegistered", userService.isRegistered(request));	
		User currentUser = (User) userRepository.findByName(n);
			if(currentUser != null) {
				model.addAttribute("user", currentUser);
				BookCollection bc = new BookCollection(name, description, BookCollection.CUSTOM);
				bc.setUser(currentUser);
				bookCollectionRepository.save(bc);	
				
			}else {
				model.addAttribute("user", "undefined");
				
			}
		
		
		return "colecciones";

	}

	@PostMapping("/micoleccion")
	public String showCollection(HttpServletRequest request,  Model model, @RequestParam String id) {
		model.addAttribute("isRegistered", userService.isRegistered(request));	
		
		BookCollection bc = bookCollectionRepository.findById(Long.parseLong(id));		
		model.addAttribute("collection",bc);
		
		return "micoleccion";
	}
	
	@PostMapping("/editarcoleccion")
	public String editCollection(HttpServletRequest request,  Model model, @RequestParam long id) {
		model.addAttribute("isRegistered", userService.isRegistered(request));	
		BookCollection bc = bookCollectionRepository.findById(id);
		
		model.addAttribute("canBeEdited",bc.getCustom());		
		model.addAttribute("collection",bc);
		
		return "editarcoleccion";
	}
	
	@PostMapping("/submitcollectionchanges")
	public String editCollection(HttpServletRequest request,  Model model, @RequestParam long id, 
				@RequestParam String name, @RequestParam String description, @RequestParam String removedBooks) {

		BookCollection bc = bookCollectionRepository.findById(id);
		model.addAttribute("isRegistered", userService.isRegistered(request));	
		String[] removedIdBooks = removedBooks.split(";"); 
		
		for(String s : removedIdBooks) {
			if(s != "") {
				Book b = bookRepository.findById(Long.parseLong(s));
				bc.removeBook(b);
			}
		}		
		
		//llamada al repo de BookCollection para updatear la tabla
		bc.setName(name);
		bc.setDescription(description);
		
		bookCollectionRepository.save(bc);
		
		model.addAttribute("canBeEdited",bc.getCustom());
		model.addAttribute("collection",bc);
		
		return "editarcoleccion";
	}
}
