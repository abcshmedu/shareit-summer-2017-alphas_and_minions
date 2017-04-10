package models;

public class Book extends Medium {

	String author;
	String isbn;
	
	Book(String title) {
		super(title);
	}
	
	Book(String title, String author, String isbn) {
		super(title);
		this.author = author;
		this.isbn = isbn;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getIsbn() {
		return isbn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Book [author=" + author + ", isbn=" + isbn + ", getTitle()=" + getTitle() + ", toString()="
				+ super.toString() + "]";
	}

	
	// todo isbn nummers might have - seperators
	public boolean checkIsbn() {
		
		if (isbn == null)
			return false;
		if (isbn.isEmpty())
			return false;
		
		int mod = 10;
		int sum = 0;
		for (int i = 0; i < isbn.length(); ++i) {
			int multi = i%2 == 0 ? 3 : 1;
			sum += Character.getNumericValue(isbn.charAt(i))*multi;
		}
		
		int div = sum/mod;
		int remainder = sum%mod;
		int checkDigit = div - remainder;
		
		if (checkDigit == Character.getNumericValue(isbn.charAt(isbn.length()-1)))
			return true;
		else
			return false;
	}
	
	
	

}
