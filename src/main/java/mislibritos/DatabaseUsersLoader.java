package mislibritos;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUsersLoader {
	 @Autowired
	 private UserRepository userRepository;
	 
	 @PostConstruct
	 private void initDatabase() {
	
	 userRepository.save(
	 new User("user", "soy un user cualquiera", "user@gmail.com", "pass","ROLE_USER"));
	 userRepository.save(
	 new User("Celia","Me gustan los libros y los gatos", "adminCelia","celia-merino1997@hotmail.com", "ROLE_USER","ROLE_ADMIN"));
	 userRepository.save(
	 new User("Marcos","Me gustan los mangas y los gatos", "adminMarcos","m.agudo.2016@alumnos.urjc.es", "ROLE_USER","ROLE_ADMIN"));
	 userRepository.save(
	 new User("Andrea","Me gustan los libros y los mangas", "adminAndrea","a.rodriguezgo.2016@alumnos.urjc.es", "ROLE_USER","ROLE_ADMIN"));
	}
}
