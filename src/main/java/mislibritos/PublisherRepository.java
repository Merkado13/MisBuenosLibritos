package mislibritos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher,Long>{
	Publisher findByName(String name);
	List<Publisher> findByNameContaining(String search);
}
