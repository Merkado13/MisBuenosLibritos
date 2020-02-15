package mislibritos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User,Long>{	
	
	User findByName(String name);
	User findById(long id);
	
	@Modifying
	@Query (value = "insert into user_book_collection (user_id, book_collection_id) VALUES(:idUser, :idColl)", nativeQuery = true)
	@Transactional
	void insertBookCollectionToUser(@Param("idUser") long idUser, @Param("idColl") long idColl);
	
	BookCollection findByBookCollection_Name(String name);
}
