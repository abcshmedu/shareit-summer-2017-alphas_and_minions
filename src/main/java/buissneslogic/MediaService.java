/**
 * 
 */
package buissneslogic;
import models.*;

/**
 * @author rebec
 *
 */
interface MediaService {

	public MediaServiceResult addBook(Book book);
	
	public MediaServiceResult addDisc(Disc disc);
	
	public Medium[] getBooks();
	
	public Medium[] getDisc();
	
	public MediaServiceResult updateBook(Book book);
	
	public MediaServiceResult updateDisc(Disc disc);
	
}
