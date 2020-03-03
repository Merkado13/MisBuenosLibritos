package mislibritos;

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
public class BookCollectionController {

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private BookCollectionRepository bookCollectionRepository;
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/colecciones")
	public String colecciones(Model model, HttpServletRequest request) {
		/*User testUser = (User)session.getAttribute("user");
		
		model.addAttribute("user", userRepository.findById(testUser.getId()));*/
		
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
		
		bookCollectionRepository.deleteById(Long.parseLong(colId));
		
		String name = request.getUserPrincipal().getName();
		
		User currentUser = (User) userRepository.findByName(name);
			if(currentUser != null) {
				model.addAttribute("user", currentUser);
				
			}else {
				model.addAttribute("user", "undefined");
				
			}
		
		
		//model.addAttribute("user", userRepository.findById(testUser.getId()));
		return "colecciones";

	}
	
	@PostMapping("/newCollection")
	public String addCollection(Model model, HttpServletRequest request, @RequestParam String name, @RequestParam String description) {
		
		/*
		User testUser = (User)session.getAttribute("user");
		BookCollection bc = new BookCollection(name, description, BookCollection.CUSTOM);
		bc.setUser(testUser);
		bookCollectionRepository.save(bc);
		
		testUser.AddCollection(bc);
		//userRepository.insertBookCollectionToUser(testUser.id, bc.getId());
		//model.addAttribute("collections", testUser.getBookCollection());
		
		model.addAttribute("user", userRepository.findById(testUser.getId()));*/
		
		String n = request.getUserPrincipal().getName();
		
		User currentUser = (User) userRepository.findByName(n);
			if(currentUser != null) {
				model.addAttribute("user", currentUser);
				BookCollection bc = new BookCollection(name, description, BookCollection.CUSTOM);
				bc.setUser(currentUser);
				bookCollectionRepository.save(bc);
				currentUser.AddCollection(bc);		
				
			}else {
				model.addAttribute("user", "undefined");
				
			}
		
		
		return "colecciones";

	}

	@GetMapping("/micoleccion/{colId}")
	public String showCollection(HttpSession session, Model model, @PathVariable long colId) {
		
		
		BookCollection bc = bookCollectionRepository.findById(colId);		
		model.addAttribute("collection",bc);
		
		return "micoleccion";
	}
	
	@GetMapping("/editarcoleccion/{colId}")
	public String editCollection(HttpSession session, Model model, @PathVariable long colId) {
		
		
		BookCollection bc = bookCollectionRepository.findById(colId);
		
		model.addAttribute("canBeEdited",bc.getCustom());		
		model.addAttribute("collection",bc);
		
		return "editarcoleccion";
	}
	
	@PostMapping("/editarcoleccion/{colId}")
	public String editCollection(HttpSession session, Model model, @PathVariable long colId, 
				@RequestParam String name, @RequestParam String description, @RequestParam String removedBooks) {
		
		BookCollection bc = bookCollectionRepository.findById(colId);
		
		String[] removedIdBooks = removedBooks.split(";"); 
		
		for(String id : removedIdBooks) {
			if(id != "") {
				Book b = bookRepository.findById(Long.parseLong(id));
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
