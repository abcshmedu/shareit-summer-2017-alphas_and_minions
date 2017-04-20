/**
 * 
 */
package buissneslogic;
import models.*;

/**
 * @author Michael Eggers
 * @author Rebecca Brydon
 */
interface MediaService {

	/**
	 * adds a book
	 * @param book
	 */
	public MediaServiceResult addBook(Book book);
	
	/**
	 * adds a disc
	 * @param disc
	 */
	public MediaServiceResult addDisc(Disc disc);
	
	/**
	 * @return
	 */
	public Medium[] getBooks();
	
	/**
	 * @return
	 */
	public Medium[] getDisc();
	
	/**
	 * @param book
	 * @return
	 */
	public MediaServiceResult updateBook(Book book);
	
	/**
	 * @param disc
	 * @return
	 */
	public MediaServiceResult updateDisc(Disc disc);
	
}
