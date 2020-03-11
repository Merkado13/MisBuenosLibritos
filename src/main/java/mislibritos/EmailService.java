package mislibritos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class EmailService {

	public static final String URI_WELCOME_EMAIL = "http://localhost:8888/welcomeemail";
	public static final String URI_NEW_BOOK_EMAIL = "http://localhost:8888/newbookemail";
	
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
	
	
	
	public void sendNewBookEmail(String author, String title) throws RestClientException, JsonProcessingException {
		
		NewBookEmailData emailData = getNewBookEmailData(author, title);		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);		
		HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(emailData),headers);
		restTemplate.postForObject(URI_NEW_BOOK_EMAIL, request, String.class);
		
	}
	
	public NewBookEmailData getNewBookEmailData(String authorName, String title) {
		
		List<User> subUsers = authorRepo.findByName(authorName).getSubUsers();
		List<String> userEmails = new ArrayList<String>();
		for(User user: subUsers) {
			userEmails.add(user.email);
		}
		
		return new NewBookEmailData(authorName, title, userEmails);
	}
	
}
