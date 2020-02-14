package mislibritos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long>{
	
	List<Book> findByIsbn(long isbn);
	Book findByTitle(String title);
	List<Book> findByTitleContaining(String search);
	
}
