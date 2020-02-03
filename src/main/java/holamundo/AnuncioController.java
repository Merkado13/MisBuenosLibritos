package holamundo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AnuncioController {

	@RequestMapping("/anuncio")
	public String anuncio(Model model) {

		return "anuncio";
	}

	@RequestMapping("/anuncioResponse")
	public String anuncioResponse(Model model, 	@RequestParam String nombre, 
												@RequestParam String asunto,
												@RequestParam String comentario) {

		model.addAttribute("nombre", nombre);
		model.addAttribute("asunto", asunto);
		model.addAttribute("comentario", comentario);

		return "anuncio_response";
	}
}
