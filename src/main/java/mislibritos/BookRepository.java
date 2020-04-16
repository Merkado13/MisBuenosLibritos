package mislibritos;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;


@CacheConfig(cacheNames="micache")
public interface BookRepository extends JpaRepository<Book,Long>{
	
	Book findByIsbn(long isbn);
	@Cacheable
	Book findByTitle(String title);
	Book findById(long id);
	List<Book> findByTitleContaining(String search);
	
	
}
