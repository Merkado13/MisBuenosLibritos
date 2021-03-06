package mislibritos;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@CacheConfig(cacheNames="bookCache")
public interface BookRepository extends JpaRepository<Book,Long>{
	
	Book findByIsbn(long isbn);
	Book findByTitle(String title);
	Book findById(long id);
	List<Book> findByTitleContaining(String search);
	
	@Cacheable
	@Query("SELECT book.title FROM Book book")
	public List<String> findTitles();
	
	@CacheEvict(allEntries=true)
	Book save(Book b);
	
	
	
	
}
