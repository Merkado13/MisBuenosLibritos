package mislibritos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.*;
public interface BookCollectionRepository extends JpaRepository<BookCollection,Long>{	
	
@Modifying
@Query (value = "insert into book_collection_books (book_collection_id, books_isbn) VALUES(:idCollection, :idBook)", nativeQuery = true)
@Transactional
void insertBookToCollection(@Param("idCollection") long idCollection, @Param("idBook") long idBook);
}
