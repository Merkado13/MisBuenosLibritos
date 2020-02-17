package mislibritos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AuthorRepository  extends JpaRepository<Author,Long>{

	Author findByName(String name);
	List<Author> findByNameContaining(String search);
	
	@Modifying
	@Query (value = "insert into book_authors (book_isbn, authors_id) VALUES(:idBook, :idAuthor)", nativeQuery = true)
	@Transactional
	void insertBookToPublishedBooks(@Param("idBook") long idBook, @Param("idAuthor") long idAuthor);
		

}
