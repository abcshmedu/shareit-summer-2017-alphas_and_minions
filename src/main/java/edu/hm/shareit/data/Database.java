package edu.hm.shareit.data;
// TODO: distinct hash-tables for books, discs, ...

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
 * 
 * @author Michael Eggers
 * @author Rebecca Brydon
 */
public class Database {

    /**
     * Hash-Table that holds all the media during runtime.
     */
    private static Map<Integer, Medium> hash2medium = new HashMap<>();

    /**
     * Add medium to database.
     * 
     * @param medium
     *            Medium to add.
     * 
     * 
     * @return Empty Optional if the medium is already in the database, Optional
     *         of added medium otherwise.
     */
    public Optional<Medium> addMedium(final Medium medium) {
        if (hash2medium.containsKey(medium.hashCode())) {
            return Optional.empty();
        }
        hash2medium.put(medium.hashCode(), medium);
        return Optional.of(medium);
    }

    /**
     * Get all books from database.
     * 
     * @return Optional of all books in database.
     */
    public Optional<List<Medium>> getBooks() {
        List<Medium> books = hash2medium.entrySet().stream().map(entry -> entry.getValue())
                .filter(type -> type instanceof Book).collect(Collectors.toList());

        return Optional.of(books);
    }

    /**
     * Get all discs from database.
     * 
     * @return Optional of all discs in database.
     */
    public Optional<List<Medium>> getDiscs() {
        List<Medium> discs = hash2medium.entrySet().stream().map(entry -> entry.getValue())
                .filter(type -> type instanceof Disc).collect(Collectors.toList());

        return Optional.of(discs);
    }

    /**
     * Get a specific book in database by its isbn.
     * 
     * @param isbn
     *            ISBN to look for.
     * 
     * 
     * @return Optional of the medium if found, empty Optional otherwise.
     */
    public Optional<Medium> getBook(final String isbn) {
        Optional<Medium> result = getBooks().get().stream().filter(disc -> ((Book) disc).getIsbn().equals(isbn))
                .findFirst();
        return result;
    }

    /**
     * Get a specific disc in database by its barcode.
     * 
     * @param barcode Barcode to look for.
     * 
     * @return Optional of the medium if found, empty Optional otherwise.
     */
    public Optional<Medium> getDisc(final String barcode) {
        Optional<Medium> result = getDiscs().get().stream().filter(disc -> ((Disc) disc).getBarcode().equals(barcode))
                .findFirst();
        return result;
    }

    /**
     * Remove a medium from the database.
     * 
     * @param medium Medium to remove.
     *            
     */
    public void remove(final Medium medium) {
        hash2medium.remove(medium.hashCode());
    }
    
    /**
     * Clear data base.
     */
    public void clearDatabse() {
        hash2medium.clear();
    }
    
    
    public void saveToken(String token, String username) {
        
        
    }
    
    public void removeToken() {
        
    }
}
