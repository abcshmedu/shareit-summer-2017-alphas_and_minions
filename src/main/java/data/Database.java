package data;

import java.util.ArrayList;

import models.Book;
import models.Disc;

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
