package mislibritos;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class EmailService {

	public static final String URI_WELCOME_EMAIL = "http://lbsi:8888/welcomeemail";
	public static final String URI_NEW_BOOK_EMAIL = "http://lbsi:8888/newbookemail";
	
	private RestTemplate restTemplate = new RestTemplate();
	private ObjectMapper objectMapper;
	
	@Autowired
	private AuthorRepository authorRepo;
	
	@PostConstruct
	public void init() {
		objectMapper = new ObjectMapper();
		//objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		
	}
	
	
	public void sendWelcomeEmail(String email) {		
		restTemplate.postForObject(URI_WELCOME_EMAIL, email, String.class);
		
	}
	
	
	
	public void sendNewBookEmail(String author, String title) throws RestClientException, JsonProcessingException, URISyntaxException {

		Map<String, String> map = new HashMap<>();
		String emails = getEmailsData(author, title);
			
		map.put("authorName", author);
		map.put("title", title);
		map.put("userEmails", emails);
		if(emails != null)
			restTemplate.postForObject("http://lbsi:8888/newbookemail/", map, String.class);
		}
	
	public String getEmailsData(String authorName, String title) {
		//System.out.println("2. DOS. Preparando datos para enviar al servidor");
		StringBuilder salida = new StringBuilder();
		List<User> subUsers = authorRepo.findByName(authorName).getSubUsers();
		if(subUsers.isEmpty()) {
			return null;
		}
		
		for(User user: subUsers) {
			System.out.println(salida);
			//userEmails.add(user.email);
			String e = user.email.toString();
			salida.append(e);
			salida.append(";");
		}
		System.out.println(salida);
		
		return salida.toString();
	}
	
}
