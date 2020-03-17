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
	
	
	public String assertBookState(Model model, Book book, User user) {
		String bookState = getBookState(book,user);
		System.out.println(bookState);
		if(bookState.equals(BookState.NONE.toString())) {
			model.addAttribute("bookState", "No Info");
			return "No info";
		}else {
			model.addAttribute("bookState",bookState);
			return bookState;
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
			//System.out.println("Hay");
			if(bookCollectionRepository.getRowFromBookCollectionBooks(col.getId(), book.getId()) != null) {
				state = BookState.values()[i];
				//System.out.println("Está, i = " +i);
				found = true;
			}
			i++;
		}
		
		return state.toString();
	}
	
	public void insertBookIntoBookCollection(Model model, Book book, BookCollection bookCollection, User user) {
		/*Buscar que el libro no está ya en alguna de las otras listas por defecto, si está en la misma, dejarlo,
		 * si está en otra, quitarlo de esa y meterlo en la nueva 
		*/		
		String bookState = getBookState(book, user);		
		
		if(!bookCollection.getCustom()) {
			BookCollection bc = null; 
			if(bookState.equals(BookState.PARA_LEER.toString())) {
				bc = bookCollectionRepository.findByNameAndUser("Por leer", user);	
				//System.out.println("Estaba en PARA LEER");
			}
			if(bookState.equals(BookState.LEYENDO.toString())) {
				bc = bookCollectionRepository.findByNameAndUser("Leyendo", user);
				//System.out.println("Estaba en LEYENDO");
			}
			if(bookState.equals(BookState.LEÍDO.toString())) {
				bc = bookCollectionRepository.findByNameAndUser("Leído", user);	
				//System.out.println("Estaba en LEÍDO");
			}
			
			if(bc!=null) {
				System.out.println("A borrar");
				bc.removeBook(book);
				bookCollectionRepository.save(bc);
			}	
		}
		
		bookCollection.addBook(book);
		bookCollectionRepository.save(bookCollection);
		
		
	}
	
}
