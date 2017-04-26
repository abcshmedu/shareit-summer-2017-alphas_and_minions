package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

	
	private static Map<Integer, Medium> hash2medium = new HashMap<>();
	
	public Database() {

	}
	
	public Optional<Medium> addMedium(final Medium medium) {
		if(hash2medium.containsKey(medium.hashCode())) {
			return Optional.of(null);
		}
		return Optional.of(hash2medium.put(medium.hashCode(), medium));
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
	
	public Optional<Medium> update(final Medium medium) {
		return Optional.of(hash2medium.put(medium.hashCode(), medium));
	}	
}

