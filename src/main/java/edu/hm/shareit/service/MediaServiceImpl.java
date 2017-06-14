/**
 * 
 */
package edu.hm.shareit.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import edu.hm.shareit.model.Book;
import edu.hm.shareit.model.Disc;
import edu.hm.shareit.model.Medium;
import edu.hm.shareit.persistence.MediaPersistence;

/**
 * @author Michael Eggers
 * @author Rebecca Brydon
 */
public class MediaServiceImpl implements MediaService {

//    private Database data = new Database();
	private MediaPersistence data;

    /**
     * 
     * MediaServiceImpl implements all business logic.
     * 
     */
	@Inject
    public MediaServiceImpl(MediaPersistence data) {
//        data = new Database();
		this.data = data;
    }

    /**
     * checks if book is valid and adds the book or returns error code.
     * 
     * @param book
     *            add this book if valid
     * @return MediaServiceResult
     */
    public MediaServiceResult addBook(Book book) {
        MediaServiceResult result = MediaServiceResult.IM_A_TEAPOT;
        if (book != null) {
            if (!book.checkIsbn()) {
                result = MediaServiceResult.INVALID_ISBN;
            } else if (book.getAuthor().isEmpty() || book.getTitle().isEmpty() || book.getIsbn().isEmpty()) {
                result = MediaServiceResult.MISSING_INFO;
            } else if (data.getBook(book.getIsbn()).isPresent()) {
                result = MediaServiceResult.ISBN_RESERVED;
            } else {
//                data.addMedium(book);
                result = MediaServiceResult.OK;
            }
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see buissneslogic.MediaService#addDisc(models.Disc)
     */
    @Override
    public MediaServiceResult addDisc(Disc disc) {
        MediaServiceResult result = MediaServiceResult.IM_A_TEAPOT;
        if (disc != null) {
            if (disc.getTitle().isEmpty() || disc.getDirector().isEmpty() || disc.getFsk() == -1) {
                result = MediaServiceResult.MISSING_INFO;
            } else if (data.getDisc(disc.getBarcode()).isPresent()) {
                result = MediaServiceResult.BARCODE_RESERVED;
            } else if (disc.getBarcode().isEmpty()) {
                result = MediaServiceResult.MISSING_BARCODE;
            } else {
//                data.addMedium(disc);
                result = MediaServiceResult.OK;
            }
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see buissneslogic.MediaService#getBooks()
     */
    @Override
    public Medium[] getBooks() {
        Book[] result = null;
        if (data.getBooks().isPresent()) {
            List<Medium> books = data.getBooks().get();
            result = new Book[books.size()];
            result = books.toArray(result);
        }

        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see buissneslogic.MediaService#getDisc()
     */
    @Override
    public Medium[] getDiscs() {
        Disc[] result = null;
        if (data.getDiscs().isPresent()) {
            List<Medium> discs = data.getDiscs().get();
            result = new Disc[discs.size()];
            result = discs.toArray(result);
        }

        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see buissneslogic.MediaService#updateBook(models.Book)
     */
    @Override
    public MediaServiceResult updateBook(Book book) {
        MediaServiceResult result;
        Optional<Medium> bookToUpdate = data.getBook(book.getIsbn());
        if (bookToUpdate.isPresent()) {
            String title = book.getTitle();
            String author = book.getAuthor();
            if (title.isEmpty()) {
                title = bookToUpdate.get().getTitle();
            }
            if (author.isEmpty()) {
                author = ((Book) (bookToUpdate.get())).getAuthor();
            }
            Medium updatedBook = new Book(title, author, book.getIsbn());
//            data.remove(bookToUpdate.get());
//            data.addMedium(updatedBook);
            result = MediaServiceResult.OK;
        } else {
            result = MediaServiceResult.NOT_FOUND;
        }

        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see buissneslogic.MediaService#updateDisc(models.Disc)
     */
    @Override
    public MediaServiceResult updateDisc(Disc disc) {
        MediaServiceResult result;
        Optional<Medium> discToUpdate = data.getDisc(disc.getBarcode());
        if (discToUpdate.isPresent()) {
            String title = disc.getTitle();
            String director = disc.getDirector();
            int fsk = disc.getFsk();
            if (title.isEmpty()) {
                title = discToUpdate.get().getTitle();
            }
            if (director.isEmpty()) {
                director = ((Disc) (discToUpdate.get())).getDirector();
            }
            if (fsk == -1) {
                fsk = ((Disc) (discToUpdate.get())).getFsk();
            }
            Medium updatedDisc = new Disc(disc.getBarcode(), director, fsk, title);
//            data.remove(discToUpdate.get());
//            data.addMedium(updatedDisc);
            result = MediaServiceResult.OK;
        } else {
            result = MediaServiceResult.NOT_FOUND;
        }

        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.hm.shareit.service.MediaService#getBook(java.lang.String)
     */
    @Override
    public Optional<Medium> getBook(String isbn) {
        Optional<Medium> book = data.getBook(isbn);
        if (book.isPresent()) {
            return book;
        } else {
            return Optional.empty();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.hm.shareit.service.MediaService#getDisc(java.lang.String)
     */
    @Override
    public Optional<Medium> getDisc(String barcode) {
        Optional<Medium> disc = data.getDisc(barcode);
        if (disc.isPresent()) {
            return disc;
        } else {
            return Optional.empty();
        }
    }
    
    /* (non-Javadoc)
     * @see edu.hm.shareit.service.MediaService#clearDatabase()
     */
    @Override
    public void clearDatabase() {
//        data.clearDatabse();
    }

}
