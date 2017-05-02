/**
 * 
 */
package edu.hm.shareit.service;

import java.util.Optional;

import edu.hm.shareit.model.*;

/**
 * @author Michael Eggers
 * @author Rebecca Brydon
 */
public interface MediaService {

	/**
	 * adds a book.
	 * 
	 * @param book to be added.
	 * 
	 * @return result
	 */
	MediaServiceResult addBook(Book book);

	/**
	 * adds a disc.
	 * 
	 * @param disc disc to be added.
	 * 
	 * @return result
	 */
	MediaServiceResult addDisc(Disc disc);

	/**
	 * gets a book. If no error result dose not change, otherwise override
	 * result with error code.
	 * 
	 * @param isbn number of book.
	 * 
	 * @return medium
	 */
	Optional<Medium> getBook(String isbn);

	/**
	 * gets a book.
	 * 
	 * @return book
	 */
	Medium[] getBooks();

	/**
	 * get Discs.
	 * 
	 * @param barcode of disc.
	 * 
	 * @return disc
	 */
	Optional<Medium> getDisc(String barcode);

	/**
	 * get discs.
	 * 
	 * @return medium
	 */
	Medium[] getDiscs();

	/**
	 * update book.
	 * 
	 * @param book update this book.
	 * 
	 * @return result
	 */
	MediaServiceResult updateBook(Book book);

	/**
	 * update disc.
	 * 
	 * @param disc update this disc
	 * 
	 * @return result
	 */
	MediaServiceResult updateDisc(Disc disc);

}
