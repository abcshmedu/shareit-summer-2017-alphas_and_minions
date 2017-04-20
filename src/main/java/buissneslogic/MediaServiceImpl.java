/**
 * 
 */
package buissneslogic;

import models.Book;
import models.Disc;
import models.Medium;


/**
 * @author Michael Eggers
 * @author Rebecca Brydon
 */

class MediaServiceImpl implements MediaService { 
	
	/**
	 * MediaServiceImpl implements all buissnes logic.
	 */
	protected MediaServiceImpl() {
	}

	
	/**
	 * checks if book is valid and adds the book or returns error code.
	 * @param book add this book if valid
	 * @return MediaServiceResult
	 */
	public MediaServiceResult addBook(Book book) {
		MediaServiceResult result = MediaServiceResult.IM_A_TEAPOT;
		if (book != null) {
			if (!book.checkIsbn()) {
				result = MediaServiceResult.INVALID_ISBN;
			} else if (book.getAuthor().isEmpty() || book.getTitle().isEmpty()) {
				result = MediaServiceResult.MISSING_INFO;
			} else {
				result = MediaServiceResult.OK;
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
			} else if (disc.getBarcode().isEmpty()) {
				result = MediaServiceResult.BARCODE_NOT_FOUND;
			} else {
				result = MediaServiceResult.OK;
			}
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see buissneslogic.MediaService#getBooks()
	 */
	@Override
	public Medium[] getBooks() {
		// gets book from Datenzugriffsschicht
		return null;
	}

	/* (non-Javadoc)
	 * @see buissneslogic.MediaService#getDisc()
	 */
	@Override
	public Medium[] getDisc() {
		return null;
	}

	/* (non-Javadoc)
	 * @see buissneslogic.MediaService#updateBook(models.Book)
	 */
	@Override
	public MediaServiceResult updateBook(Book book) {
		return MediaServiceResult.OK;
	}

	/* (non-Javadoc)
	 * @see buissneslogic.MediaService#updateDisc(models.Disc)
	 */
	@Override
	public MediaServiceResult updateDisc(Disc disc) {
		return MediaServiceResult.OK;
	}
	
	
	
}
