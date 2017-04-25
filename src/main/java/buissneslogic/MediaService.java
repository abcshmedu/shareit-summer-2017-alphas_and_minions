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
	 * @param isbn
	 * @param result
	 * @return
	 */
	public Medium getBook(String isbn, MediaServiceResult result);


	/**
	 * @param result
	 * @return
	 */
	public Medium[] getBooks(MediaServiceResult result);
	

	/**
	 * @param barcode
	 * @param result
	 * @return
	 */
	public Medium getDisc(String barcode, MediaServiceResult result);
	
	/**
	 * @param result
	 * @return
	 */
	public Medium[] getDiscs(MediaServiceResult result);
	
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
