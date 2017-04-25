/**
 * 
 */
package buissneslogic;

import models.Book;
import models.Disc;
import models.Medium;

import java.util.ArrayList;

import org.json.JSONObject;

import data.Database;


/**
 * @author Michael Eggers
 * @author Rebecca Brydon
 */

class MediaServiceImpl implements MediaService { 
	
	Database data;
	/**
	 * MediaServiceImpl implements all buissnes logic.
	 */
	protected MediaServiceImpl() {
		this.data = new Database();
	}

	
	/**
	 * checks if book is valid and adds the book or returns error code.
	 * @param book add this book if valid
	 * @return MediaServiceResult
	 */
	public MediaServiceResult addBook(Book book) {
		MediaServiceResult result = MediaServiceResult.IM_A_TEAPOT; // TODO sensible default value
		if (book != null) {
			if (!book.checkIsbn()) {
				result = MediaServiceResult.INVALID_ISBN;
			} else if (book.getAuthor().isEmpty() || book.getTitle().isEmpty()) {
				result = MediaServiceResult.MISSING_INFO;
			} else {
				if (data.isDublicateIsbn(book.getIsbn())) {
					result = MediaServiceResult.ISBN_RESERVED;
				} else {
					data.addBook(book);
					result = MediaServiceResult.OK;	
				}
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
				result = MediaServiceResult.MISSING_BARCODE;
			} else {
				if (data.isDublicateBarcode(disc.getBarcode())) {
					result = MediaServiceResult.BARCODE_RESERVED;
				} else {
					data.addDisc(disc);
					result = MediaServiceResult.OK;					
				}
			}
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see buissneslogic.MediaService#getBook(buissneslogic.MediaServiceResult)
	 */
	public Medium getBook(String isbn, MediaServiceResult result) {
		Book book = data.getBook(isbn);
		if (book == null) {
			result = MediaServiceResult.NOT_FOUND;
		}
		return book; // TODO no book found -> what should be returned??
	}

	/* (non-Javadoc)
	 * @see buissneslogic.MediaService#getBooks()
	 */
	@Override
	public Medium[] getBooks(MediaServiceResult result) {
		Medium[] books = (Medium[]) data.getDatastorageBooks().toArray();
		
		if (books != null){
			if (books.length == 0) {
				result = MediaServiceResult.NO_BOOKS;
			}
		}
		return books;
	}
	
	/* (non-Javadoc)
	 * @see buissneslogic.MediaService#getDisc(java.lang.String, buissneslogic.MediaServiceResult)
	 */
	public Medium getDisc(String barcode, MediaServiceResult result) {
		Medium disc = data.getDisc(barcode);
		
		if (disc == null) {
			result = MediaServiceResult.NOT_FOUND;
		}
		return disc;
	}
	
	/* (non-Javadoc)
	 * @see buissneslogic.MediaService#getDisc()
	 */
	@Override
	public Medium[] getDiscs(MediaServiceResult result) {
		Medium[] discs = (Medium[]) data.getDatastorageDiscs().toArray();
		
		if (discs != null) {
			if (discs.length == 0) {
				result = MediaServiceResult.NO_DISCS;
			}
		}
		return discs; // TODO review
	}

	/* (non-Javadoc)
	 * @see buissneslogic.MediaService#updateBook(models.Book)
	 */
	@Override
	public MediaServiceResult updateBook(Book book) {
		
		Book oldBook = data.getBook(book.getIsbn());
		
		// TODO book only contains information to be changed. What if isbn changes?
		
		return MediaServiceResult.OK;
	}

	/* (non-Javadoc)
	 * @see buissneslogic.MediaService#updateDisc(models.Disc)
	 */
	@Override
	public MediaServiceResult updateDisc(Disc disc) {
		
		// TODO book only contains information to be changed. What if barcode changes?
		return MediaServiceResult.OK;
	}
	
	
	
}
