package edu.hm.shareit.model;

/**
 * Book represents a book.
 * 
 * @author Michael Eggers
 * @author Rebecca Brydon
 */
public class Book extends Medium {

	private final String author;
	private final String isbn; // make final? see MediaResource updateBook
	
	/**
	 * Book empty constructor for jackson.
	 */
	public Book() {
		super("");
		author = "";
		isbn = "";
	}
	
	/**
	 * Book creates book.
	 * @param title Title the books shall have.
	 * @param author Author of the book.
	 * @param isbn The book's ISBN.
	 */
	public Book(String title, String author, String isbn) {
		super(title);
		this.author = author;
		this.isbn = isbn;
	}
	
	
	/**
	 * Gets the author.
	 * @return author Author of this book.
	 */
	public String getAuthor() {
		return author;
	}
	
	/**
	 * Gets ISBN.
	 * @return isbn ISBN of this book.
	 */
	public String getIsbn() {
		return isbn;
	}

	/* (non-Javadoc)
	 * @see models.Medium#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see models.Medium#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;			
		}
		if (!super.equals(obj)) {
			return false;			
		}
		if (getClass() != obj.getClass()) {
			return false;			
		}
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null) {
				return false;				
			}
		} else if (!author.equals(other.author)) {
			return false;			
		}
		if (isbn == null) {
			if (other.isbn != null) {
				return false;				
			}
		} else if (!isbn.equals(other.isbn)) {
			return false;			
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see models.Medium#toString()
	 */
	@Override
	public String toString() {
		return "Book [author=" + author + ", isbn=" + isbn + ", getTitle()=" + getTitle() + ", toString()="
				+ super.toString() + "]";
	}

	
	// TODO : Checkstyle complains about magic numbers and missing {} at if.
	/**
	 * checks if isbn is valid.
	 * @return bool
	 */
	public boolean checkIsbn() {
		// todo isbn nummers deal with - seperators in isbn
		
		if (isbn == null)
			return false;
		if (isbn.isEmpty())
			return false;
		if (isbn.length() < 13)
			return false;
		
		int mod = 10;
		int sum = 0;
		for (int i = 0; i < isbn.length()-1; ++i) {
			int multi = (i+1)%2 == 0 ? 3 : 1;
			sum += Character.getNumericValue(isbn.charAt(i))*multi;
		}
		
		int div = sum/mod;
		int remainder = sum%mod;
		int checkDigit = mod - remainder;
		
		if (checkDigit == Character.getNumericValue(isbn.charAt(isbn.length()-1)))
			return true;
		else
			return false;
	}
}
