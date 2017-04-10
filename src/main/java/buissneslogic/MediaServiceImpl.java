/**
 * 
 */
package buissneslogic;

import models.Book;
import models.Disc;
import models.Medium;

/**
 * @author rebec
 *
 */

class MediaServiceImpl implements MediaService { 
	
	protected MediaServiceImpl() {
		
	}


	public MediaServiceResult addBook(Book book) {
		MediaServiceResult result;
		if (!book.checkIsbn()) {
			result = MediaServiceResult.INVALID_ISBN;
		} else {
			result = MediaServiceResult.OK;
		}
		
		return result;
	}

	@Override
	public MediaServiceResult addDisc(Disc disc) {
		return MediaServiceResult.OK;
	}

	@Override
	public Medium[] getBooks() {
		return null;
	}

	@Override
	public Medium[] getDisc() {
		return null;
	}

	@Override
	public MediaServiceResult updateBook(Book book) {
		return MediaServiceResult.OK;
	}

	@Override
	public MediaServiceResult updateDisc(Disc disc) {
		return MediaServiceResult.OK;
	}
	
	
	
}
