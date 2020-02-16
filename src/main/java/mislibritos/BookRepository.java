package mislibritos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface BookRepository extends JpaRepository<Book,Long>{
	
	List<Book> findByIsbn(long isbn);
	Book findByTitle(String title);
	Book findById(long id);
	List<Book> findByTitleContaining(String search);
	
}
