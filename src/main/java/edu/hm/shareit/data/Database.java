package edu.hm.shareit.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import edu.hm.shareit.model.Book;
import edu.hm.shareit.model.Disc;
import edu.hm.shareit.model.Medium;

/**
 * Simple Generic database to save JSONobj.
 * @author Michael Eggers
 * @author Rebecca Brydon
 */
public class Database {

	
	private static Map<Integer, Medium> hash2medium = new HashMap<>();

	
	public Optional<Medium> addMedium(final Medium medium) {
		if(hash2medium.containsKey(medium.hashCode())) {
			return Optional.empty();
		}
		hash2medium.put(medium.hashCode(), medium);
		return Optional.of(medium);
	}
	
	public Optional<List<Medium>> getBooks() {
		List<Medium> books = hash2medium.entrySet().stream()
				.map(entry -> entry.getValue())
				.filter(type -> type instanceof Book)
				.collect(Collectors.toList());
		
		return Optional.of(books);
	}
	
	public Optional<List<Medium>> getDiscs() {
		List<Medium> discs = hash2medium.entrySet().stream()
				.map(entry -> entry.getValue())
				.filter(type -> type instanceof Disc)
				.collect(Collectors.toList());
		
		return Optional.of(discs);
	}
	
	public Optional<Medium> getBook(final String isbn) {
		Optional<Medium> result = getBooks().get().stream()
				.filter(disc -> ((Book)disc).getIsbn().equals(isbn))
				.findFirst();
		return result;
	}
	
	public Optional<Medium> getDisc(final String barcode) {
		Optional<Medium> result = getDiscs().get().stream()
				.filter(disc -> ((Disc)disc).getBarcode().equals(barcode))
				.findFirst();
		return result;
	}
	
	public Optional<Medium> update(final Medium medium) {
		Book incomingBook = (Book)medium;
		Optional<Medium> bookToModify = getBook(incomingBook.getIsbn());
		if (bookToModify.isPresent()) {
			hash2medium.remove(bookToModify.get().hashCode());
			hash2medium.put(medium.hashCode(), medium);
			Medium updatedBook = hash2medium.get(medium.hashCode());
			return Optional.of(updatedBook);
		}
		return Optional.empty();
	}
	
	public void remove(final Medium medium) {
		hash2medium.remove(medium.hashCode());
	}
}

