package mislibritos;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUsersLoader {
	 @Autowired
	 private UserRepository userRepository;
	 @Autowired
	 private UserService userService;
	 
	 BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	 @PostConstruct
	 private void initDatabase() {
		 
		 
		 userService.getNewUser("user", "soy un user cualquiera", "user@gmail.com", "pass","ROLE_USER");
		 userService.getNewUser("Celia","Me gustan los libros y los gatos", "celia-merino1997@hotmail.com", passwordEncoder.encode("adminCelia"), "ROLE_USER","ROLE_ADMIN");
		 userService.getNewUser("Marcos","Me gustan los mangas y los gatos", "m.agudo.2016@alumnos.urjc.es", passwordEncoder.encode("adminMarcos"), "ROLE_USER","ROLE_ADMIN");
		 userService.getNewUser("Andrea","Me gustan los libros y los mangas", "a.rodriguezgo.2016@alumnos.urjc.es",passwordEncoder.encode("adminAndrea"), "ROLE_USER","ROLE_ADMIN");
		 /*	
	 userRepository.save(
	 new User("user", "soy un user cualquiera", "user@gmail.com", "pass","ROLE_USER"));
	 userRepository.save(
	 new User("Celia","Me gustan los libros y los gatos", "celia-merino1997@hotmail.com", passwordEncoder.encode("adminCelia"), "ROLE_USER","ROLE_ADMIN"));
	 userRepository.save(
	 new User("Marcos","Me gustan los mangas y los gatos", "m.agudo.2016@alumnos.urjc.es", passwordEncoder.encode("adminMarcos"), "ROLE_USER","ROLE_ADMIN"));
	 userRepository.save(
	 new User("Andrea","Me gustan los libros y los mangas", "a.rodriguezgo.2016@alumnos.urjc.es",passwordEncoder.encode("adminAndrea"), "ROLE_USER","ROLE_ADMIN"));
	
	*/
	
	}
}
