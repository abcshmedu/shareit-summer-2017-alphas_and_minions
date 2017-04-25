package data;

import java.util.ArrayList;

import buissneslogic.MediaServiceResult;
import models.Book;
import models.Disc;
import models.Medium;

/**
 * Simple Generic database to save JSONobj.
 * @author Michael Eggers
 * @author Rebecca Brydon
 */
public class Database {

	ArrayList<Book> datastorageBooks;
	ArrayList<Disc> datastorageDiscs;
	
	public Database() {
		this.datastorageBooks = new ArrayList<Book>();
		this.datastorageDiscs = new ArrayList<Disc>();
	}
	
	/**
	 * Adds book to database.
	 * @param book
	 */
	public void addBook(Book book) {
		datastorageBooks.add(book);
	}
	
	/**
	 * Adds disc to database.
	 * @param disc
	 */
	public void addDisc(Disc disc) {
		datastorageDiscs.add(disc);
	}
	
	public boolean isDublicateIsbn(String isbn) {
		for (Book book : datastorageBooks) {
			if (book.getIsbn().equals(isbn)) {
				return true;
			} else {
				return false;
			}
		}
		return false; // no books jet
	}
	
	public boolean isDublicateBarcode(String barcode) {
		for (Disc disc : datastorageDiscs) {
			if (disc.getBarcode().equals(barcode)) {
				return true;
			} else {
				return false;
			}
		}
		return false; // no disc jet
	}
	
	/**
	 * gets book.
	 * @param isbn
	 * @return
	 */
	public Book getBook(String isbn) {
		for (Book book : datastorageBooks) {
			if (book.getIsbn().equals(isbn)) {
				return book;
			} else {
				return null;
			}
		}
	}
	
	/**
	 * gets disc.
	 * @param barcode
	 * @return
	 */
	public Disc getDisc(String barcode) {
		for (Disc disk : datastorageDiscs) {
			if (disk.getBarcode().equals(barcode)) {
				return disk;
			} else {
				return null;
			}
		}
	}

	/**
	 * @return the datastorageBooks
	 */
	public ArrayList<Book> getDatastorageBooks() {
		return datastorageBooks;
	}

	/**
	 * @param datastorageBooks the datastorageBooks to set
	 */
	public void setDatastorageBooks(ArrayList<Book> datastorageBooks) {
		this.datastorageBooks = datastorageBooks;
	}

	/**
	 * @return the datastorageDiscs
	 */
	public ArrayList<Disc> getDatastorageDiscs() {
		return datastorageDiscs;
	}

	/**
	 * @param datastorageDiscs the datastorageDiscs to set
	 */
	public void setDatastorageDiscs(ArrayList<Disc> datastorageDiscs) {
		this.datastorageDiscs = datastorageDiscs;
	}
	
	
}
