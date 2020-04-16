package mislibritos;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
@CacheConfig(cacheNames="micache")
public interface UserRepository extends CrudRepository<User,Long>{	

	User findByName(String name);
	User findById(long id);
	User findByEmail(String email);
	
	@Modifying
	@Query (value = "insert into user_book_collection (user_id, book_collection_id) VALUES(:idUser, :idColl)", nativeQuery = true)
	@Transactional
	void insertBookCollectionToUser(@Param("idUser") long idUser, @Param("idColl") long idColl);
	
	BookCollection findByBookCollection_Name(String name);
	

	
}
