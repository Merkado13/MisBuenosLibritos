package mislibritos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book,Long>{
	
	Book findByIsbn(long isbn);
	Book findByTitle(String title);
	Book findById(long id);
	List<Book> findByTitleContaining(String search);
	
	@Query("SELECT book.title FROM Book book")
	public List<String> findTitles();
	
	
	
	
}
