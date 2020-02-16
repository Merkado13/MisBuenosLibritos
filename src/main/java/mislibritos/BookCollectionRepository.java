package mislibritos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.*;
public interface BookCollectionRepository extends JpaRepository<BookCollection,Long>{	
	
	@Modifying
	@Query (value = "insert into book_collection_books (book_collection_id, books_id) VALUES(:idCollection, :idBook)", nativeQuery = true)
	@Transactional
	void insertBookToCollection(@Param("idCollection") long idCollection, @Param("idBook") long idBook);
	
	BookCollection findByNameAndUser(String name, User user);
	
	List<BookCollection> findFirst3ByNameContainingAndUser(String name, User user);
	List<BookCollection> findFirst3ByDescriptionContainingAndUser(String description, User user);
	
	BookCollection findById(long colId);
	
	@Query( value = "select * from book_collection_books where book_collection_id = ?1 and books_id = ?2", nativeQuery = true)
	Object getRowFromBookCollectionBooks(long bcId, long bookId);
	
	//void updateNameAndDescription(String name, String description);
}
