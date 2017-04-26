/**
 * 
 */
package buissneslogic;

import models.Book;
import models.Disc;
import models.Medium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import org.json.JSONObject;

import data.Database;


/**
 * @author Michael Eggers
 * @author Rebecca Brydon
 */

class MediaServiceImpl implements MediaService { 
	
	private final Database data;
	
	/**
	 * MediaServiceImpl implements all buissnes logic.
	 */
	protected MediaServiceImpl() {
		data = new Database();
	}

	
	/**
	 * checks if book is valid and adds the book or returns error code.
	 * @param book add this book if valid
	 * @return MediaServiceResult
	 */
	public MediaServiceResult addBook(Book book) {
		MediaServiceResult result = MediaServiceResult.IM_A_TEAPOT; // TODO
																	// sensible
																	// default
																	// value
		if (book != null) {
			if (!book.checkIsbn()) {
				result = MediaServiceResult.INVALID_ISBN;
			} 
			else if (book.getAuthor().isEmpty() || book.getTitle().isEmpty()) {
				result = MediaServiceResult.MISSING_INFO;
			} 
			else {
				if(data.addMedium(book).isPresent())
					return MediaServiceResult.ISBN_RESERVED;
				else
					return MediaServiceResult.OK;
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see buissneslogic.MediaService#addDisc(models.Disc)
	 */
	@Override
	public MediaServiceResult addDisc(Disc disc) {
		MediaServiceResult result = MediaServiceResult.IM_A_TEAPOT;
		if (disc != null) {
			if (disc.getTitle().isEmpty() || disc.getDirector().isEmpty()) {
				result = MediaServiceResult.MISSING_INFO;
			} 
			else if (disc.getBarcode().isEmpty()) {
				result = MediaServiceResult.MISSING_BARCODE;
			} 
			else {
				if(data.addMedium(disc).isPresent())
					return MediaServiceResult.BARCODE_RESERVED;
				else
					return MediaServiceResult.OK;
			}
		}
		return result;
	}


	/* (non-Javadoc)
	 * @see buissneslogic.MediaService#getBooks()
	 */
	@Override
	public Medium[] getBooks() {
		Book[] result = null;
		if(data.getBooks().isPresent())
			data.getBooks().get().toArray(result);
		
		return result;
	}
	
	
	/* (non-Javadoc)
	 * @see buissneslogic.MediaService#getDisc()
	 */
	@Override
	public Medium[] getDiscs() {
		Disc[] result = null;
		if(data.getDiscs().isPresent())
			data.getDiscs().get().toArray(result);
		
		return result;
	}

	/* (non-Javadoc)
	 * @see buissneslogic.MediaService#updateBook(models.Book)
	 */
	@Override
	public MediaServiceResult updateBook(Book book) {
		if(data.update(book).isPresent())
			return MediaServiceResult.OK;
		
		return MediaServiceResult.NOT_FOUND;
	}

	/* (non-Javadoc)
	 * @see buissneslogic.MediaService#updateDisc(models.Disc)
	 */
	@Override
	public MediaServiceResult updateDisc(Disc disc) {
		if(data.update(disc).isPresent())
			return MediaServiceResult.OK;
		
		return MediaServiceResult.NOT_FOUND;
	}
	
	
	
}
