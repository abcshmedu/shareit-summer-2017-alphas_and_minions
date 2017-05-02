package edu.hm.shareit.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.hm.shareit.model.Book;

public class BookTest {
	
	String title = "Calculus";
	String author = "Adams";
	String isbn = "978-0-306-40615-7";
	String isbn2 = "978-3-16-148410-0";
	
	
	public BookTest() {
	}
	
	@Test
	public void isbnTest() {
		Book testB = new Book(title,author,isbn);
		Book testB2 = new Book(title,author,isbn2);
		
		assertTrue(testB.checkIsbn());
		assertTrue(testB2.checkIsbn());
		
	}
	
}
