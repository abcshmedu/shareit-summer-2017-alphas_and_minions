package edu.hm.shareit.test;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;

import edu.hm.shareit.model.Book;
import edu.hm.shareit.model.Disc;
import edu.hm.shareit.model.Medium;
import edu.hm.shareit.service.MediaServiceImpl;
import edu.hm.shareit.service.MediaServiceResult;

public class MediaServiceImplTest {

	final String title = "Calculus";
	final String author = "Adams";
	final String author2 = "Strang";
	final String title2 = "Computation Science";
	final String isbn = "978-0-306-40615-7";
	final String isbn2 = "978-3-16-148410-0";
	final String invalidISBN = "978-3-16-148410-8";
	final String title3 = "Guardians of the Galaxy";
	final String director = "Star Lord";
	final String barcode = "0000000000";
	final String barcode2 = "0000000001";
	final int fsk = 18;
	MediaServiceResult result = MediaServiceResult.IM_A_TEAPOT;
	Book testB1 = new Book(title, author, isbn);
	Book testB2 = new Book(title, author, isbn2);
	MediaServiceImpl service;

	public MediaServiceImplTest() {
		service = new MediaServiceImpl();
		initialiseDatabase();
	}

	public void initialiseDatabase() {

	}

	@Test
	public void bookTest() {
		// add book
		result = service.addBook(testB1);
		assertEquals(MediaServiceResult.OK, result);
		result = service.addBook(testB2);
		assertEquals(MediaServiceResult.OK, result);

		// isbn reserved
		result = service.addBook(testB1);
		assertEquals(MediaServiceResult.ISBN_RESERVED, result);
		// invalid isbn
		result = service.addBook(new Book(title, author, invalidISBN));
		assertEquals(MediaServiceResult.INVALID_ISBN, result);
		// missing info
		result = service.addBook(new Book("", author, isbn));
		assertEquals(MediaServiceResult.MISSING_INFO, result);
		result = service.addBook(new Book(title, "", isbn));
		assertEquals(MediaServiceResult.MISSING_INFO, result);
		result = service.addBook(null);
		assertEquals(MediaServiceResult.IM_A_TEAPOT, result);

		// get books
		Book[] testBooks = new Book[2];
		testBooks[0] = testB1;
		testBooks[1] = testB2;
		Book[] books = (Book[]) service.getBooks();

		assertArrayEquals(books, testBooks);

		// update book
		result = service.updateBook(new Book("","",testB1.getIsbn()));
		assertEquals(MediaServiceResult.OK, result);
		
		Book update = new Book(title2, author2, testB2.getIsbn());
		result = service.updateBook(update);
		assertEquals(MediaServiceResult.OK, result);
		// ok
		Optional<Medium> test = service.getBook(testB2.getIsbn());
		assertTrue(test.isPresent());
		Book bookUpdated = (Book) test.get();
		assertEquals(bookUpdated.getAuthor(), author2);
		assertEquals(bookUpdated.getTitle(), title2);
		// not found
		result = service.updateBook(new Book("xxx", "xxx", "978-3-16-148410-4"));
		assertEquals(MediaServiceResult.NOT_FOUND, result);

		// get books

		testBooks[1] = update;
		books = (Book[]) service.getBooks();
		assertArrayEquals(books, testBooks);
		
		Optional<Medium> updated = service.getBook("154685312154");
		assertFalse(updated.isPresent());

	}

	@Test
	public void discTest() {
		MediaServiceResult result = MediaServiceResult.IM_A_TEAPOT;
		Disc disc1 = new Disc(barcode, director, fsk, title3);
		Disc disc2 = new Disc(barcode2, director, fsk, title3);

		// ok
		result = service.addDisc(disc1);
		assertEquals(MediaServiceResult.OK, result);
		result = service.addDisc(disc2);
		assertEquals(MediaServiceResult.OK, result);
		// barcode reserved
		result = service.addDisc(disc1);
		assertEquals(MediaServiceResult.BARCODE_RESERVED, result);
		
		// missing barcode
		result = service.addDisc(new Disc("",director,fsk,title3));
		assertEquals(MediaServiceResult.MISSING_BARCODE,result);
		
//		TODO invalid barcode
//		result = service.addDisc(new Disc("2",director,fsk,title3));
//		assertEquals(MediaServiceResult.INVALID_BARCODE,result);
		// missing info
		result = service.addDisc(new Disc(barcode,"",fsk,title3));
		assertEquals(MediaServiceResult.MISSING_INFO,result);
		// get discs
		Disc[] discs = (Disc[]) service.getDiscs();
		Disc[] testDiscs = new Disc[2];
		testDiscs[0] = disc1;
		testDiscs[1] = disc2;
		assertArrayEquals(testDiscs, discs);
		// update discs
		final int fsk2 = 12;
		result = service.updateDisc(new Disc(barcode,"",fsk2,""));
		assertEquals(MediaServiceResult.OK,result);
		result = service.updateDisc(new Disc(barcode,"",-1,"The Guardians of the Galaxy 2"));
		assertEquals(MediaServiceResult.OK,result);
		result = service.updateDisc(new Disc(barcode,"Rocket",-1,""));
		assertEquals(MediaServiceResult.OK,result);
		result = service.updateDisc(disc1);
		assertEquals(MediaServiceResult.OK,result);
		
		result = service.updateDisc(new Disc("00333","",fsk2,""));
		assertEquals(MediaServiceResult.NOT_FOUND, result);
		
		// get discs
		Optional<Medium> updated = service.getDisc(barcode);
		assertTrue(updated.isPresent());
		assertEquals(updated.get(),disc1);
		updated = service.getDisc("5548814");
		assertFalse(updated.isPresent());
		
		
	}

}
