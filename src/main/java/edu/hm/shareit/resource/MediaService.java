/**
 * 
 */
package edu.hm.shareit.resource;
import java.util.Optional;

import edu.hm.shareit.model.*;

/**
 * @author Michael Eggers
 * @author Rebecca Brydon
 */
public interface MediaService {

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
	public Optional<Medium> getBook(String isbn);


	/**
	 * @param result
	 * @return
	 */
	public Medium[] getBooks();
	

	/**
	 * @param barcode
	 * @param result
	 * @return
	 */
	public Optional<Medium> getDisc(String barcode);
	
	/**
	 * @param result
	 * @return
	 */
	public Medium[] getDiscs();
	
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
