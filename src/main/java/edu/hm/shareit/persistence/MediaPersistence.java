package edu.hm.shareit.persistence;

import java.util.List;
import java.util.Optional;

import edu.hm.shareit.model.Medium;

/**
 * Interface for a database. Used for DI.
 * 
 * @author Rebecca Brydon, Michael Eggers
 *
 */
public interface MediaPersistence {
	
	/**
	 * Adds a book or disc.
	 * 
	 * @param medium Medium to add.
	 * @return added medium or empty.
	 */
	Optional<Medium> addMedium(Medium medium);
	
	/**
	 * Get all books.
	 * 
	 * @return List of all the books, or empty if no books in DB.
	 */
	Optional<List<Medium>> getBooks();
	
	/**
	 * Get specific book.
	 * 
	 * @param isbn ISBN of wanted book.
	 * @return Book if found, empty if not found.
	 */
	Optional<Medium> getBook(final String isbn);
	
	/**
	 * Get all discs.
	 * 
	 * @return List of all the discs, or empty if no discs in DB.
	 */
	Optional<List<Medium>> getDiscs();
	
	/**
	 * Get a specific disc.
	 * 
	 * @param barcode Barcode of wanted disc.
	 * @return Disc if found, empty if not found.
	 */
	Optional<Medium> getDisc(final String barcode);
	
	/**
	 * Remove a medium from DB.
	 * 
	 * @param id ID (which is ISBN or Barcode) of medium to remove.
	 */
	void remove(final String id);
	
	/**
	 * Updates a specific medium if available.
	 * 
	 * @param medium Medium with updated information.
	 */
	void update(final Medium medium);

}
