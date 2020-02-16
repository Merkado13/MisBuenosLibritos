package mislibritos;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class BookService {

	@Autowired
	private BookCollectionRepository bookCollectionRepository;
	
	
	public void assertBookState(Model model, Book book, User user) {
		String bookState = getBookState(book,user);
		
		if(bookState.equals(BookState.NONE.toString())) {
			model.addAttribute("bookState", "No Info");
		}else {
			model.addAttribute("bookState",bookState);
		}	
	}
	
	private String getBookState(Book book, User user) {
		//Pillar las collecciones del usuario
		List<BookCollection> defaultCollections = bookCollectionRepository.findFirst3ByDescriptionContainingAndUser("Libros que", user);
		
		BookState state = BookState.NONE;
		
		Iterator iterator = defaultCollections.iterator();
		boolean found = false;
		int i = 0;
		while(iterator.hasNext() && !found) {
			BookCollection col = (BookCollection)iterator.next();
			
			if(bookCollectionRepository.getRowFromBookCollectionBooks(col.getId(), book.getId()) != null) {
				state = BookState.values()[i];
				found = true;
			}
			i++;
		}
		
		return state.toString();
	}
	
}
