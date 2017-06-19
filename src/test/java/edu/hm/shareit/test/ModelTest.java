package edu.hm.shareit.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.hm.shareit.model.Book;
import edu.hm.shareit.model.Disc;

public class ModelTest {

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

    public ModelTest() {
    }

    @Test
    public void isbnTest() {
        Book testB = new Book(title, author, isbn);
        Book testB2 = new Book(title, author, isbn2);

        assertTrue(testB.checkIsbn());
        assertTrue(testB2.checkIsbn());

    }

    @Test
    public void discTest() {
        Disc disc1 = new Disc(barcode, director, fsk, title3);
        assertEquals("Disc [" + "barcode=" + barcode + ", " + "director=" + director + ", " + "fsk=" + fsk + ", "
                + "getTitle()=" + title3 + "]", disc1.toString());
        assertEquals(barcode, disc1.getID());
        assertEquals(director, disc1.getDirector());
        assertEquals(fsk, disc1.getFsk());
        assertEquals(title3, disc1.getTitle());

    }

}
