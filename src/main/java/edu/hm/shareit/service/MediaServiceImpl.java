/**
 * 
 */
package edu.hm.shareit.service;

import java.util.List;
import java.util.Optional;

import edu.hm.shareit.data.Database;
import edu.hm.shareit.model.Book;
import edu.hm.shareit.model.Disc;
import edu.hm.shareit.model.Medium;


/**
 * @author Michael Eggers
 * @author Rebecca Brydon
 */

public class MediaServiceImpl implements MediaService { 
	
	private Database data = new Database();
	
	/**
	 * MediaServiceImpl implements all buissnes logic.
	 */
	public MediaServiceImpl() {
		data = new Database();
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
			} 
			if (book.getAuthor().isEmpty() || book.getTitle().isEmpty() || book.getIsbn().isEmpty()) {
				result = MediaServiceResult.MISSING_INFO;
			} 
			else {
				if(!data.addMedium(book).isPresent())
					result = MediaServiceResult.ISBN_RESERVED;
				else
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
			if (disc.getTitle().isEmpty() || disc.getDirector().isEmpty() || disc.getBarcode().isEmpty()) {
				result = MediaServiceResult.MISSING_INFO;
			} 
			else if (disc.getBarcode().isEmpty()) {
				result = MediaServiceResult.MISSING_BARCODE;
			} 
			else {
				if(data.addMedium(disc).isPresent())
					result = MediaServiceResult.BARCODE_RESERVED;
				else
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
		Book[] result = null;
		if(data.getBooks().isPresent()) {
			List<Medium> books = data.getBooks().get();
			result = new Book[books.size()];
			result = books.toArray(result);
		}	
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
		MediaServiceResult result;
		Optional<Medium> bookToUpdate = data.getBook(book.getIsbn());
		if (bookToUpdate.isPresent()) {
			String title = book.getTitle();
			String author = book.getAuthor();
			if (title.isEmpty())
				title = bookToUpdate.get().getTitle();
			if (author.isEmpty())
				author = ((Book)(bookToUpdate.get())).getAuthor();
			Medium updatedBook = new Book(title, author, book.getIsbn());
			data.remove(bookToUpdate.get());
			data.addMedium(updatedBook);
			result = MediaServiceResult.OK;
		}
		else {
			result = MediaServiceResult.NOT_FOUND;
		}
		
		return result;
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
	
	
	@Override
	public Optional<Medium> getBook(String isbn) {
		Optional<Medium> book = data.getBook(isbn);
		if (book.isPresent())
			return book;
		else
			return Optional.empty();
	}
	
	@Override
	public Optional<Medium> getDisc(String barcode) {
		Optional<Medium> disc = data.getDisc(barcode);
		if (disc.isPresent())
			return disc;
		else 
			return Optional.empty();
	}
	
	
	
}
