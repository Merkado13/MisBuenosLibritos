package mislibritos;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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
	
	@PostConstruct
	public void init() {
		
		bookRepository.save(new Book("La Biblia"));
		bookRepository.save(new Book("El Necronomicón"));
		
		BookCollection librosSagrados = new BookCollection("Libros Sagrados");
		librosSagrados.AddBook(bookRepository.findByName("La Biblia"));
		librosSagrados.AddBook(bookRepository.findByName("El Necronomicón"));
		
		bookCollectionRepository.save(librosSagrados);
		
	}
	
	@RequestMapping("/home")
	public String home(Model model) {
		
		
		model.addAttribute("bookitos", bookCollectionRepository.findAll());
		
		return "home";
	}
	@RequestMapping("/colecciones")
	public String colecciones(Model model) {
		//model.addAttribute("pagina_anterior", anterior);
		return "colecciones";
		
	}
	@RequestMapping("/busqueda")
	public String busqueda(Model model, @RequestParam String input) {
		model.addAttribute("input", input);
		return "busqueda";
		
	}
	@RequestMapping("/perfil")
	public String perfil(Model model) {
		
		return "perfil";
		
	}
	

}