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
	 * gets a book. If no error result dose not change, otherwise override result with error code.
	 * @param result
	 * @return
	 */
	public Medium getBook(MediaServiceResult result);


	/**
	 * @param result
	 * @return
	 */
	public Medium[] getBooks(MediaServiceResult result);
	
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
