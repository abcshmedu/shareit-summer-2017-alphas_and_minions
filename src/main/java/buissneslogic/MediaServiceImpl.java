/**
 * 
 */
package buissneslogic;

import models.Book;
import models.Disc;
import models.Medium;
import buissneslogic.MediaResource;

/**
 * @author Michael Eggers
 * @author Rebecca Brydon
 */

class MediaServiceImpl implements MediaService { 
	
	MediaResource mediaResource;
	
	/**
	 * MediaServiceImpl implements all buissnes logic.
	 */
	protected MediaServiceImpl() {
		this.mediaResource = new MediaResource();
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
				result = MediaServiceResult.MISSING_BOOK_INFO;
			} else {
				mediaResource.createBook(book);
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
		return MediaServiceResult.OK;
	}

	/* (non-Javadoc)
	 * @see buissneslogic.MediaService#getBooks()
	 */
	@Override
	public Medium[] getBooks() {
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
