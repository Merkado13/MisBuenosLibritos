package holamundo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class GreetingController {

	@RequestMapping("/greeting")
	public String greeting(Model model) {
		
		
		//model.addAttribute("name", name);
		
		return "greeting";
	}
	
	@RequestMapping("/greetingResponse")
	public String greetingResponse(Model model, @RequestParam String name) {
		
		model.addAttribute("name", name);
		
		return "greeting_response";
	}
	
}
